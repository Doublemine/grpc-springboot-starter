package work.wanghao.kotlin.boot.grpc.configure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import work.wanghao.kotlin.boot.grpc.annotation.GrpcClient
import work.wanghao.kotlin.boot.grpc.bootstrap.GrpcClientRunner
import work.wanghao.kotlin.boot.grpc.condition.ScannerGrpcClientCondition
import work.wanghao.kotlin.boot.grpc.property.GrpcClientProperties

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-01-12
 *  Time: 10:29
 *  Description:
 **/
@AutoConfigureOrder
@ConditionalOnBean(annotation = [(GrpcClient::class)])
@EnableConfigurationProperties(GrpcClientProperties::class)
class GrpcClientAutoConfigure {

    @Autowired
    private lateinit var gRpcClientProperties: GrpcClientProperties

    @Bean
    @Conditional(ScannerGrpcClientCondition::class)
    fun gRpcClientRunner(): GrpcClientRunner {
        return GrpcClientRunner(gRpcClientProperties)
    }

}