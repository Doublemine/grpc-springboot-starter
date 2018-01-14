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
        @NestedConfigurationProperty var client: Map<String, GrpcClientNodeProperties> = HashMap<String, GrpcClientNodeProperties>().apply {
            put("defaultClient", GrpcClientNodeProperties())
        })

data class GrpcClientNodeProperties(var host: String = "localhost",
                                    var port: Int = 9494,
                                    var plaintext: Boolean = true,
                                    var enableKeepAlive: Boolean = false,
                                    var keepAliveWithoutCalls: Boolean = false,
                                    var keepAliveTime: Long = 180, var keepAliveTimeout: Long = 40)

