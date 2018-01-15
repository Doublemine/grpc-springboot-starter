package work.wanghao.kotlin.boot.grpc.sample.service;

import io.grpc.Channel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import work.wanghao.kotlin.boot.grpc.annotation.GrpcClientChannel;
import work.wanghao.kotlin.boot.grpc.sample.GreeterGrpc;
import work.wanghao.kotlin.boot.grpc.sample.HelloReply;
import work.wanghao.kotlin.boot.grpc.sample.HelloRequest;

/**
 * @author doublemine
 * Created on 2018/01/14 19:49.
 */

@Service
public class GrpcClientService {

    @GrpcClientChannel()
    private Channel channel;

    public String sendMessage(String name) {
        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
        HelloReply response = stub.sayHello(HelloRequest.newBuilder().setName(name).build());
        return response.getMessage();
    }
}
