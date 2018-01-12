package work.wanghao.kotlin.boot.grpc.bootstrap

import io.grpc.ClientInterceptor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.context.support.AbstractApplicationContext
import work.wanghao.kotlin.boot.grpc.annotation.GrpcClientChannel
import work.wanghao.kotlin.boot.grpc.condition.GrpcChannelScan
import work.wanghao.kotlin.boot.grpc.property.GrpcClientProperties
import java.util.stream.Collectors
import kotlin.reflect.KClass

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-12
 *  Time: 11:21
 *  Description:
 **/
class GrpcClientRunner(properties: GrpcClientProperties) : CommandLineRunner, DisposableBean {
    private val logger = LoggerFactory.getLogger(GrpcClientRunner::class.java)

    private val nodeProperties = properties

    @Autowired
    private lateinit var appContext: AbstractApplicationContext

    override fun run(vararg args: String?) {
        logger.info("gogogo")

        /*find inject scan annotation value*/
        val obj = appContext.beanFactory.getBeansWithAnnotation(GrpcChannelScan::class.java)
                .entries.stream().findFirst().get().value

        val annotationValue = obj.javaClass.getDeclaredAnnotation(GrpcChannelScan::class.java)

        if (annotationValue.basePackage.java.name == GrpcChannelScan::class.java.name && annotationValue.basePackagePath.isEmpty()) {
            logger.error("Must Specified scan path.")
            return
        }

        val scanPath = if (annotationValue.basePackage.java.name != GrpcChannelScan::class.java.name) {
            annotationValue.basePackage.java.`package`.name
        } else {
            annotationValue.basePackagePath
        }

        val scan = ClassPathScanningCandidateComponentProvider(false)
        scan.addIncludeFilter({ metadataReader, _ ->
            filterByAnnotation(metadataReader.classMetadata.className, GrpcClientChannel::class)
        })
        val beanDef = scan.findCandidateComponents(scanPath)

        build(beanDef)

    }

    override fun destroy() {

    }

    private fun build(set: Set<BeanDefinition>) {

        val dd = set.map {
            val map = appContext.classLoader.loadClass(it.beanClassName).declaredFields.filter {
                it.isAnnotationPresent(GrpcClientChannel::class.java)
            }.filter {
                val annotation = it.getDeclaredAnnotation(GrpcClientChannel::class.java)
                annotation != null
            }.map {

                val map = LinkedHashMap<String, Array<KClass<out ClientInterceptor>>>()
                map.put(it.getDeclaredAnnotation(GrpcClientChannel::class.java)
                        .value, it.getDeclaredAnnotation(GrpcClientChannel::class.java)
                        .interceptors)
                map

            }.distinctBy { it }
            map
        }


        set.map { appContext.classLoader.loadClass(it.beanClassName).declaredFields }
                .flatMap { it.asIterable() }
                .filter { it.isAnnotationPresent(GrpcClientChannel::class.java) }
                .filter { it.getDeclaredAnnotation(GrpcClientChannel::class.java) != null }
                .map {
                    val map = LinkedHashMap<String, Array<KClass<out ClientInterceptor>>>()
                    map.put(it.getDeclaredAnnotation(GrpcClientChannel::class.java)
                            .value, it.getDeclaredAnnotation(GrpcClientChannel::class.java)
                            .interceptors)
                    map
                }.distinct()

    }

    private fun filterByAnnotation(className: String, annotation: KClass<out Annotation>): Boolean {
        return appContext.classLoader.loadClass(
                className).declaredFields.any { it.isAnnotationPresent(annotation.java) }
    }

}