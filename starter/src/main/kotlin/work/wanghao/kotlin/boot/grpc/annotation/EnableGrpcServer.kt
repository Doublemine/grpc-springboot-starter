package work.wanghao.kotlin.boot.grpc.annotation

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-11
 *  Time: 13:46
 *  Description:
 **/
@MustBeDocumented
@Target(allowedTargets = [(AnnotationTarget.CLASS)])
@Retention
annotation class EnableGrpcServer

@MustBeDocumented
@Target(allowedTargets = [(AnnotationTarget.CLASS)])
@Retention
annotation class EnableGrpcClient