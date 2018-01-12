package work.wanghao.kotlin.boot.grpc.annotation

import org.springframework.stereotype.Component

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-12
 *  Time: 11:38
 *  Description:
 **/
@MustBeDocumented
@Target(allowedTargets = [(AnnotationTarget.CLASS)])
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class GrpcClient