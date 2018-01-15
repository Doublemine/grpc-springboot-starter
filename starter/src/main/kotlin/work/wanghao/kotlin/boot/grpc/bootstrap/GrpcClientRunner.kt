package work.wanghao.kotlin.boot.grpc.bootstrap

import io.grpc.ClientInterceptor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.support.AbstractApplicationContext
import org.springframework.util.ReflectionUtils
import work.wanghao.kotlin.boot.grpc.annotation.GlobalInterceptor
import work.wanghao.kotlin.boot.grpc.annotation.GrpcClientChannel
import work.wanghao.kotlin.boot.grpc.factory.GrpcClientFactory
import work.wanghao.kotlin.boot.grpc.property.GrpcClientProperties
import java.lang.reflect.Field
import kotlin.reflect.KClass

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-12
 *  Time: 11:21
 *  Description:
 **/
class GrpcClientRunner(properties: GrpcClientProperties) : CommandLineRunner {
    private val logger = LoggerFactory.getLogger(GrpcClientRunner::class.java)

    private val nodeProperties = properties

    @Autowired
    private lateinit var appContext: AbstractApplicationContext

    @Autowired
    private lateinit var factory: GrpcClientFactory

    override fun run(vararg args: String?) {
        logger.info("initial gRPC Client...")

        /*find global interceptors*/
        val globalInterceptors = appContext.ensureInjectType(GlobalInterceptor::class.java,
                ClientInterceptor::class.java)
                .map { appContext.beanFactory.getBean(it, ClientInterceptor::class.java) }.toMutableList()


        appContext.beanFactory.beanDefinitionNames.map {
            appContext.beanFactory.getBean(it)
        }.filter {
            it.javaClass.declaredFields.any { it.isAnnotationPresent(GrpcClientChannel::class.java) }
        }.forEach { beanInstance ->
            beanInstance.javaClass.declaredFields.filter { it.getDeclaredAnnotation(GrpcClientChannel::class.java) != null }
                    .map {
                        GrpcChannel {
                            field = it
                            name = it.getDeclaredAnnotation(GrpcClientChannel::class.java).value
                            interceptors = it.getDeclaredAnnotation(GrpcClientChannel::class.java)
                                    .interceptors.toList()
                        }
                    }.distinct()
                    .filter {
                        nodeProperties.client.containsKey(it.name)
                    }.forEach {
                ReflectionUtils.makeAccessible(it.field)
                ReflectionUtils.setField(it.field, beanInstance, factory.createChannel(it.name, it.interceptors.map {
                    if (appContext.getBeanNamesForType(it.java).isNotEmpty()) appContext.getBean(it.java) else it.java.newInstance()
                }.plus(globalInterceptors).distinct()))
                if (logger.isDebugEnabled)
                    logger.info("Success registered channel name:${it.name} for target: ${beanInstance.javaClass.name} --> ${it.field!!.name}")
            }


        }

    }


}

class GrpcChannel {
    var field: Field? = null
    var name: String = ""
    var interceptors: List<KClass<out ClientInterceptor>> = ArrayList()
}

inline fun GrpcChannel(channel: GrpcChannel.() -> Unit): GrpcChannel {
    val result = GrpcChannel()
    channel(result)
    return result
}