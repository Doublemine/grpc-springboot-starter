package work.wanghao.kotlin.boot.grpc.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import work.wanghao.kotlin.boot.grpc.annotation.EnableGrpcServer;
import work.wanghao.kotlin.boot.grpc.annotation.GrpcService;

@SpringBootApplication
@EnableGrpcServer
public class SampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}
