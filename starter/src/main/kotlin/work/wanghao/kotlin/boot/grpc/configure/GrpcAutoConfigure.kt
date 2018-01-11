package work.wanghao.kotlin.boot.grpc.configure

import io.grpc.ServerBuilder
import io.grpc.services.HealthStatusManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import work.wanghao.kotlin.boot.grpc.condition.EnableGrpcServerCondition
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
@ConditionalOnBean(annotation = [(GrpcService::class)])
@EnableConfigurationProperties(GrpcServerProperties::class)
open class GrpcAutoConfigure {

    @Autowired
    private lateinit var gRpcServerProperties: GrpcServerProperties

    @Bean
    @Conditional(EnableGrpcServerCondition::class)
    open fun gRpcServerRunner(factory: GrpcServerFactory): GrpcServerRunner {
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