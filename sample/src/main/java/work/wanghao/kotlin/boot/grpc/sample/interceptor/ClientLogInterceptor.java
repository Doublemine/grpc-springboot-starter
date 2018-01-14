package work.wanghao.kotlin.boot.grpc.sample.interceptor;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.wanghao.kotlin.boot.grpc.annotation.GlobalClientInterceptor;

/**
 * @author doublemine
 * Created on 2018/01/14 19:46.
 */

@GlobalClientInterceptor
public class ClientLogInterceptor implements ClientInterceptor {
    private static final Logger log = LoggerFactory.getLogger(ClientLogInterceptor.class);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        log.info("client interceptor" + method.getFullMethodName());
        return next.newCall(method, callOptions);
    }
}
