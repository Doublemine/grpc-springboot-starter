package work.wanghao.kotlin.boot.grpc.property

import org.springframework.boot.context.properties.ConfigurationProperties

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-11
 *  Time: 11:44
 *  Description:
 **/

@ConfigurationProperties("grpc.server")
data class GrpcServerProperties(var port: Int = 9494, var enable: Boolean = false)