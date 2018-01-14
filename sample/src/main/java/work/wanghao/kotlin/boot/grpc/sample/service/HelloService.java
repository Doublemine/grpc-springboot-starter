package work.wanghao.kotlin.boot.grpc.sample.service;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import work.wanghao.kotlin.boot.grpc.annotation.GrpcClientChannel;
import work.wanghao.kotlin.boot.grpc.annotation.GrpcService;
import work.wanghao.kotlin.boot.grpc.sample.GreeterGrpc;
import work.wanghao.kotlin.boot.grpc.sample.HelloReply;
import work.wanghao.kotlin.boot.grpc.sample.HelloRequest;

/**
 * @author doublemine
 * Created on 2018/01/11 22:53.
 */
@GrpcService(applyGlobalInterceptor = true)
public class HelloService extends GreeterGrpc.GreeterImplBase {

    @GrpcClientChannel("test")
    private Channel channel;

    @GrpcClientChannel()
    private Channel channel2;

    @GrpcClientChannel("test")
    private Channel channel3;

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
