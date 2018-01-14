package work.wanghao.kotlin.boot.grpc.condition

import org.springframework.beans.BeansException
import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata
import work.wanghao.kotlin.boot.grpc.annotation.EnableGrpcServer

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-11
 *  Time: 13:52
 *  Description:
 **/
class EnableGrpcServerCondition : Condition {

    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
        val size = try {
            context.beanFactory.getBeansWithAnnotation(
                    EnableGrpcServer::class.java).size
        } catch (e: BeansException) {
            0
        }
        return if (size > 0) true else context.environment.getProperty(
                "grpc.enable-server", Boolean::class.java, false)

    }

}