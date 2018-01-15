package work.wanghao.kotlin.boot.grpc.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import work.wanghao.kotlin.boot.grpc.annotation.EnableGrpcClient;
import work.wanghao.kotlin.boot.grpc.annotation.EnableGrpcServer;

@SpringBootApplication
@EnableGrpcServer
@EnableGrpcClient
public class SampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}
