package work.wanghao.kotlin.boot.grpc.condition

import org.springframework.beans.BeansException
import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata
import org.springframework.stereotype.Component
import work.wanghao.kotlin.boot.grpc.annotation.GrpcClientChannel
import java.util.stream.Collectors
import kotlin.reflect.KClass

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-12
 *  Time: 11:41
 *  Description:
 **/
class ScannerGrpcClientCondition : Condition {
    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {

        val map = try {
            context.beanFactory.getBeansWithAnnotation(GrpcChannelScan::class.java)
        } catch (e: BeansException) {
            HashMap<String, Any>()
        }

        if (map.isEmpty()) {
            return false
        }

        return map.filter {
            checkAnnotationInject(it.value.javaClass)
        }.isNotEmpty()

    }

    private fun checkAnnotationInject(clazz: Class<*>): Boolean {
        return clazz.declaredFields.filter {
            it.isAnnotationPresent(GrpcClientChannel::class.java)
        }.stream().collect(Collectors.toList()).isNotEmpty()

    }

}

@MustBeDocumented
@Target(allowedTargets = [(AnnotationTarget.CLASS)])
@Retention
@Component
annotation class GrpcChannelScan(val basePackage: KClass<*> = GrpcChannelScan::class,
        val basePackagePath: String = "")