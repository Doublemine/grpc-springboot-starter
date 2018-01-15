package work.wanghao.kotlin.boot.grpc.configure

import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import work.wanghao.kotlin.boot.grpc.annotation.EnableGrpcClient
import work.wanghao.kotlin.boot.grpc.bootstrap.GrpcClientRunner
import work.wanghao.kotlin.boot.grpc.factory.DefaultGrpcClientFactory
import work.wanghao.kotlin.boot.grpc.factory.GrpcClientFactory
import work.wanghao.kotlin.boot.grpc.property.GrpcClientProperties

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-12
 *  Time: 10:29
 *  Description:
 **/

@AutoConfigureOrder
@ConditionalOnBean(annotation = [(EnableGrpcClient::class)])
@ConditionalOnProperty(name = ["grpc.client.enable"], havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(GrpcClientProperties::class)
class GrpcClientAutoConfigure {


    @Bean
    fun gRpcClientRunner(properties: GrpcClientProperties): GrpcClientRunner {
        return GrpcClientRunner(properties)
    }

    @Bean
    @ConditionalOnMissingBean(GrpcClientFactory::class)
    fun gRpcFactory(properties: GrpcClientProperties): GrpcClientFactory {
        return DefaultGrpcClientFactory(properties)
    }

}