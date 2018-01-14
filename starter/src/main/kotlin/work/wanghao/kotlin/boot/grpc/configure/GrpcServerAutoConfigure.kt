package work.wanghao.kotlin.boot.grpc.configure

import io.grpc.ServerBuilder
import io.grpc.services.HealthStatusManager
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import work.wanghao.kotlin.boot.grpc.annotation.EnableGrpcServer
import work.wanghao.kotlin.boot.grpc.annotation.GrpcService
import work.wanghao.kotlin.boot.grpc.bootstrap.GrpcServerRunner
import work.wanghao.kotlin.boot.grpc.factory.GrpcServerFactory
import work.wanghao.kotlin.boot.grpc.property.GrpcServerProperties

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-11
 *  Time: 11:34
 *  Description:
 **/
@AutoConfigureOrder
@ConditionalOnBean(annotation = [(EnableGrpcServer::class)])
@ConditionalOnProperty(name = ["grpc.enable-server"], havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(GrpcServerProperties::class)
open class GrpcServerAutoConfigure {


    @Bean
    @ConditionalOnBean(annotation = [(GrpcService::class)])
    open fun gRpcServerRunner(factory: GrpcServerFactory, gRpcServerProperties: GrpcServerProperties): GrpcServerRunner {
        return GrpcServerRunner(factory, ServerBuilder.forPort(gRpcServerProperties.port))
    }

    @Bean
    @ConditionalOnMissingBean(GrpcServerFactory::class)
    open fun gRpcServerFactory(): GrpcServerFactory {
        return GrpcServerFactory()
    }

    @Bean
    open fun healthStatusManager(): HealthStatusManager {
        return HealthStatusManager()
    }

}