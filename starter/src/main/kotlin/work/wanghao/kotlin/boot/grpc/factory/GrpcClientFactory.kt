package work.wanghao.kotlin.boot.grpc.factory

import io.grpc.Channel
import io.grpc.ClientInterceptor
import io.grpc.ManagedChannelBuilder
import org.slf4j.LoggerFactory
import work.wanghao.kotlin.boot.grpc.property.GrpcClientProperties
import java.util.concurrent.TimeUnit

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-12
 *  Time: 11:24
 *  Description:
 **/
interface GrpcClientFactory {
    fun createChannel(name: String, interceptors: List<ClientInterceptor>): Channel
}


class DefaultGrpcClientFactory(properties: GrpcClientProperties) : GrpcClientFactory {
    private val logger = LoggerFactory.getLogger(DefaultGrpcClientFactory::class.java)
    private val propertiesList = properties


    override fun createChannel(name: String, interceptors: List<ClientInterceptor>): Channel {
        val instanceProperty = propertiesList.client[name]!!

        val builder = ManagedChannelBuilder.forAddress(instanceProperty.host, instanceProperty.port)
                .usePlaintext(instanceProperty.plaintext)

        if (instanceProperty.enableKeepAlive) {
            builder.keepAliveTime(instanceProperty.keepAliveTime, TimeUnit.SECONDS)
                    .keepAliveTimeout(instanceProperty.keepAliveTimeout, TimeUnit.SECONDS)
                    .keepAliveWithoutCalls(instanceProperty.keepAliveWithoutCalls)
                    .intercept(interceptors)
        }
        return builder.build()

    }
}