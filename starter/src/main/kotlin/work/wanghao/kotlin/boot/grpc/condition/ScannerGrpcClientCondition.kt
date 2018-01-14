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


        return context.beanFactory.beanDefinitionNames.map {
            try {
                context.beanFactory.getBean(it)
            } catch (e: BeansException) {
                null
            }
        }.filter { it != null }
                .any { it!!.javaClass.declaredFields.any { it.isAnnotationPresent(GrpcClientChannel::class.java) } }

    }

}

