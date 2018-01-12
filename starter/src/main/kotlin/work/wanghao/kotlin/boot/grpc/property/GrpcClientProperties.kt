package work.wanghao.kotlin.boot.grpc.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-12
 *  Time: 10:47
 *  Description:
 **/
@ConfigurationProperties("g-rpc")
data class GrpcClientProperties(
        @NestedConfigurationProperty val client: Map<String, GrpcClientNodeProperties> = HashMap<String, GrpcClientNodeProperties>().apply {
            put("defaultClient", GrpcClientNodeProperties())
        })

data class GrpcClientNodeProperties(val host: String = "localhost",
        val port: Int = 9494,
        val plaintext: Boolean = true,
        val enableKeepAlive: Boolean = false,
        val keepAliveWithoutCalls: Boolean = false,
        val keepAliveTime: Long = 180, val keepAliveTimeout: Long = 40)

