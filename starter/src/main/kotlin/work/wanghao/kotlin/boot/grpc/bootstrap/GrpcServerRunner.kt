package work.wanghao.kotlin.boot.grpc.bootstrap

import io.grpc.*
import io.grpc.health.v1.HealthCheckResponse
import io.grpc.services.HealthStatusManager
import org.slf4j.LoggerFactory
import org.springframework.beans.BeansException
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.support.AbstractApplicationContext
import work.wanghao.kotlin.boot.grpc.annotation.GlobalInterceptor
import work.wanghao.kotlin.boot.grpc.annotation.GrpcService
import work.wanghao.kotlin.boot.grpc.factory.GrpcServerFactory
import java.util.stream.Collectors
import java.util.stream.Stream

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-11
 *  Time: 13:16
 *  Description:
 **/
open class GrpcServerRunner(factory: GrpcServerFactory, builder: ServerBuilder<*>) :
        CommandLineRunner, DisposableBean {
    private val logger = LoggerFactory.getLogger(GrpcServerRunner::class.java)
    @Autowired
    private lateinit var healthStatusManager: HealthStatusManager

    @Autowired
    private lateinit var applicationContext: AbstractApplicationContext

    private lateinit var server: Server

    private val serverFactory = factory

    private val serverBuilder = builder

    override fun run(vararg args: String?) {

        logger.info("initial gRPC Server...")

        val globalInterceptors = applicationContext.ensureInjectType(GlobalInterceptor::class.java,
                ServerInterceptor::class.java)
                .map { applicationContext.beanFactory.getBean(it, ServerInterceptor::class.java) }
                .stream().collect(Collectors.toList())

        /*add health check service*/
        serverBuilder.addService(healthStatusManager.healthService)

        if (applicationContext.ensureInjectType(GrpcService::class.java, BindableService::class.java).count() <= 0 && logger.isInfoEnabled) {
            logger.info("not found GrpcService component!")
        }

        /*bind interceptor and add healthStatus*/
        applicationContext.ensureInjectType(GrpcService::class.java, BindableService::class.java)
                .forEach {
                    val bindableService = applicationContext.beanFactory.getBean(it, BindableService::class.java)
                    val serviceDef = bindableService.bindService()
                    val gRpcService = applicationContext.findAnnotationOnBean(it, GrpcService::class.java)
                    serverBuilder.addService(bindInterceptors(serviceDef, gRpcService, globalInterceptors))
                    healthStatusManager.setStatus(serviceDef.serviceDescriptor.name, HealthCheckResponse.ServingStatus.SERVING)
                    logger.info("${bindableService::class.java.name} service has been bind.")
                }

        serverFactory.configure(serverBuilder)
        server = serverBuilder.build().start()

        logger.info("gRPC Server started on port(s): ${server.port} (tcp)")

        await4Stop()

    }

    override fun destroy() {
        server.services.forEach {
            healthStatusManager.clearStatus(it.serviceDescriptor.name)
        }
        server.shutdown()
        logger.info("gRPC Server has stop.")
    }

    private fun await4Stop() {
        Thread({
            this.server.awaitTermination()
        }).apply {
            isDaemon = false
        }.start()

    }


    private fun bindInterceptors(serviceDefinition: ServerServiceDefinition, gRpcService: GrpcService, globalInterceptors: List<ServerInterceptor>): ServerServiceDefinition {

        val partInterceptors = gRpcService.interceptors.map {
            if (applicationContext.getBeanNamesForType(it.java).isNotEmpty()) applicationContext.getBean(it.java) else it.java.newInstance()
        }

        val interceptors =
                Stream.concat(partInterceptors.stream(), if (gRpcService.applyGlobalInterceptor) globalInterceptors.stream() else Stream.empty())
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList())
        return ServerInterceptors.intercept(serviceDefinition, interceptors)

    }


}

/**
 * ensure annotation inject the target type
 */
fun <T> AbstractApplicationContext.ensureInjectType(annotationType: Class<out Annotation>, ensureType: Class<T>): List<String> {
    val matchAnnotationMap =
            try {
                getBeansWithAnnotation(annotationType)
            } catch (e: BeansException) {
                emptyMap<String, Any>()
            }
    return getBeanNamesForType(ensureType)
            .filter {
                if (!matchAnnotationMap.isEmpty()) matchAnnotationMap.contains(it) else false
            }
}