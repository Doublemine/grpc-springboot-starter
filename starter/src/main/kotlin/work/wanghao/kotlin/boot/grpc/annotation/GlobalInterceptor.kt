package work.wanghao.kotlin.boot.grpc.annotation

import org.springframework.stereotype.Component

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-15
 *  Time: 13:54
 *  Description:
 **/
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention
@Component
annotation class GlobalInterceptor