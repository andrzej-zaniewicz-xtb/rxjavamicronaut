package com.example;

import helloworld.GreeterGrpc;
import helloworld.ReactorGreeterGrpc;
import helloworld.Users;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
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
        return request
                .doOnNext(req -> log.info(" Fetched user: " + req.getName()))
                .flatMapMany(req -> userService.fetchUsers())
                .delayElements(Duration.ofMillis(50))
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
                .map(request1 ->
                        Users.HelloResponse.newBuilder()
                                .setMessage(request1.getName())
                                .build()
                );

    }

}
