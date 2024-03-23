package com.example;

import helloworld.GreeterGrpc;
import helloworld.ReactorGreeterGrpc;
import helloworld.Users;
import io.grpc.stub.StreamObserver;
import io.opentracing.Tracer;
import io.opentracing.contrib.reactor.TracedSubscriber;
import io.opentracing.util.GlobalTracer;
import io.opentracing.util.ThreadLocalScopeManager;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Singleton
@Slf4j
public class UsersGrpcService extends ReactorGreeterGrpc.GreeterImplBase {

    private final UserService userService;

    public UsersGrpcService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Flux<Users.HelloResponse> sayHello(Mono<Users.HelloRequest> request) {
        log.info(" Start grpc");
        return userService.fetchUsers()
                .doOnNext(user -> log.info(" Fetched user: " + user.getName()))
                .delayElements(Duration.ofMillis(500))
                .doOnNext(user -> log.info(" Delayed user: " + user.getName()))
                .onErrorResume(throwable -> {
                    // Log the error
                    log.error("Error fetching users: ", throwable);

                    // Return an "empty" user to indicate an error
                    User errorUser = new User();
                    errorUser.setId("-1");
                    errorUser.setName("Error");
                    errorUser.setEmail("error@example.com");
                    return Mono.just(errorUser);
                })
                .map(user -> Users.HelloResponse.newBuilder()
                        .setMessage(user.getName())
                        .build());

    }

}
