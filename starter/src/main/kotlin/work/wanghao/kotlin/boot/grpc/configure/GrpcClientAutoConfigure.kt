package work.wanghao.kotlin.boot.grpc.configure

import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import work.wanghao.kotlin.boot.grpc.bootstrap.GrpcClientRunner
import work.wanghao.kotlin.boot.grpc.condition.ScannerGrpcClientCondition
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
@Conditional(ScannerGrpcClientCondition::class)
@EnableConfigurationProperties(GrpcClientProperties::class)
class GrpcClientAutoConfigure {


    @Bean
    @Conditional(ScannerGrpcClientCondition::class)
    fun gRpcClientRunner(properties: GrpcClientProperties): GrpcClientRunner {
        return GrpcClientRunner(properties)
    }

    @Bean
    @ConditionalOnMissingBean(GrpcClientFactory::class)
    fun gRpcFactory(properties: GrpcClientProperties): GrpcClientFactory {
        return DefaultGrpcClientFactory(properties)
    }

}