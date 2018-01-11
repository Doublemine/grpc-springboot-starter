package work.wanghao.kotlin.boot.grpc.annotation

import org.springframework.stereotype.Component

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-11
 *  Time: 15:03
 *  Description:
 **/

@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class GlobalInterceptor