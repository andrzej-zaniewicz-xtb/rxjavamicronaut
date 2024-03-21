package com.example;

import helloworld.GreeterGrpc;
import helloworld.ReactorGreeterGrpc;
import helloworld.Users;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
@Slf4j
public class UsersGrpcService extends ReactorGreeterGrpc.GreeterImplBase {

    @Override
    public Mono<Users.HelloResponse> sayHello(Mono<Users.HelloRequest> request) {
        log.info(" Start grpc");
        return request
                .doOnNext(request1 -> log.info(" Fetched user: " + request1.getName()))
                .map(request1 ->
                        Users.HelloResponse.newBuilder()
                                .setMessage(request1.getName())
                                .build()
                );

    }

}
