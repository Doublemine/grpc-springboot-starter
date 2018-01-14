package work.wanghao.kotlin.boot.grpc.sample.factory;

import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import org.jetbrains.annotations.NotNull;
import work.wanghao.kotlin.boot.grpc.factory.GrpcClientFactory;

import java.util.List;

/**
 * @author doublemine
 * Created on 2018/01/12 22:21.
 */
public class CustomFactory implements GrpcClientFactory {

    @NotNull
    @Override
    public Channel createChannel(@NotNull String name, @NotNull List<? extends ClientInterceptor> interceptors) {
        return null;
    }
}
