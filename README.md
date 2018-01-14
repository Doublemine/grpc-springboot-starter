### gRPC SpringBoot Starter [![](https://jitpack.io/v/Doublemine/grpc-springboot-starter.svg)](https://jitpack.io/#Doublemine/grpc-springboot-starter)


### TODO

- [x] add client interceptor


### Usage

#### Gradle

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

#### Maven

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


### Sample
see the [sample module](https://github.com/Doublemine/grpc-springboot-starter/tree/master/sample/src/main/java/work/wanghao/kotlin/boot/grpc/sample)