package work.wanghao.kotlin.boot.grpc.sample.interceptor;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import work.wanghao.kotlin.boot.grpc.annotation.GlobalInterceptor;

/**
 * @author doublemine
 * Created on 2018/01/11 22:55.
 */
@GlobalInterceptor
public class LogInterceptor implements ServerInterceptor {


    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class.getName());

    static final Metadata.Key<String> CUSTOM_HEADER_KEY =
            Metadata.Key.of("custom_server_header_key", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        logger.info("header received from client:" + headers);

        return next.startCall(new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
            @Override
            public void sendHeaders(Metadata responseHeaders) {
                responseHeaders.put(CUSTOM_HEADER_KEY, "customRespondValue");
                super.sendHeaders(responseHeaders);
            }
        }, headers);
    }
}
