package work.wanghao.kotlin.boot.grpc.annotation

import io.grpc.ClientInterceptor
import org.springframework.beans.factory.annotation.Autowired
import kotlin.reflect.KClass

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-12
 *  Time: 10:30
 *  Description:
 **/

@MustBeDocumented
@Target(AnnotationTarget.FIELD)
@Retention
annotation class GrpcClientChannel(val value: String = "defaultClient",
        val interceptors: Array<KClass<out ClientInterceptor>> = [])