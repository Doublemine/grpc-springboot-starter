### gRPC Spring Boot Starter [![](https://jitpack.io/v/Doublemine/grpc-springboot-starter.svg)](https://jitpack.io/#Doublemine/grpc-springboot-starter)[![Build Status](https://travis-ci.org/Doublemine/grpc-springboot-starter.svg?branch=master)](https://travis-ci.org/Doublemine/grpc-springboot-starter)


### TODO

- [x] add client interceptor


### Usage


#### dependencies


##### Gradle

Step 1. Add the JitPack repository to your build file


Add it in your root build.gradle at the end of repositories:


```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency
 

```gradle
	dependencies {
	        compile 'com.github.Doublemine.grpc-springboot-starter:starter:1.0'
	}
```

##### Maven

Step 1. Add the JitPack repository to your build file

```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

```

Step 2. Add the dependency


```xml
	<dependency>
	    <groupId>com.github.Doublemine.grpc-springboot-starter</groupId>
	    <artifactId>starter</artifactId>
	    <version>1.0</version>
	</dependency>
```

#### Grpc Server:

##### Enable Server

 - Use annotation `@EnableGrpcServer` or `grpc.server.enable=true` in application.properties to Enable Grpc Server.
 - The default server port is `9494`.
 - You can change the host and port in application.properties:
 
 ```properties
grpc.server.enable=true
```

##### GrpcService

Annotate your server interface implementation(s) with ``@GrpcService``

```java
@GrpcService
public class HelloService extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
```

##### Interceptor

As private Intercept Or Annotate your ServerInterceptor interface implementation(s) with ``@GlobalInterceptor``

```java

@GlobalInterceptor // As Global interceptor
public class ServerLogInterceptor implements ServerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class.getName());

	static final Metadata.Key<String> CUSTOM_HEADER_KEY =
			Metadata.Key.of("custom_server_header_key", Metadata.ASCII_STRING_MARSHALLER);

	@Override
	public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
			Metadata headers, ServerCallHandler<ReqT, RespT> next) {
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
```
Annotate your server interface implementation(s) with ``@GrpcService``

```java
@GrpcService(applyGlobalInterceptor = true)/*or you can use private interceptor or both*/
//@GrpcService(interceptors = ServerLogInterceptor.class)
public class HelloService extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
```




#### Grpc Client:

##### Enable Client

 - Use annotation `@EnableGrpcClient` or `grpc.client.enable=true` in application.properties to Enable Grpc Server.
 
 ##### Property
 
 
  - The Annotation `@GrpcClientChannel` default value is `defaultClient` if not present to inject gRPC channel.
  the default host is `localhost` and default port is `9494`.
 
 
 ```properties
grpc.client.(gRPC server name).host=127.0.0.1
grpc.client.(gRPC server name).port=9494
# optional
grpc.client.(gRPC server name).plaintext=true
grpc.client.(gRPC server name).enable-keep-alive=false
grpc.client.(gRPC server name).keep-alive-without-calls=false
grpc.client.(gRPC server name).keep-alive-time=180
grpc.client.(gRPC server name).keep-alive-timeout=40
```
 
Then, you can inject channel:

```java
public class GrpcClientService {

    @GrpcClientChannel("<gRPC server name>")
    private Channel channel;

    public String sendMessage(String name) {
        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
        HelloReply response = stub.sayHello(HelloRequest.newBuilder().setName(name).build());
        return response.getMessage();
    }
}
```

### Sample
see the [sample module](https://github.com/Doublemine/grpc-springboot-starter/tree/master/sample/src/main/java/work/wanghao/kotlin/boot/grpc/sample)