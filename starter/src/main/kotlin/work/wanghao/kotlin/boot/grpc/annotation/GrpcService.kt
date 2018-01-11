package work.wanghao.kotlin.boot.grpc.annotation

import io.grpc.ServerInterceptor
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-11
 *  Time: 11:36
 *  Description:
 **/
@Retention(AnnotationRetention.RUNTIME)
@Target(allowedTargets = [(AnnotationTarget.CLASS), (AnnotationTarget.TYPE)])
@MustBeDocumented
@Service
annotation class GrpcService(val interceptors: Array<KClass<out ServerInterceptor>> = [],
        val applyGlobalInterceptor: Boolean = false)