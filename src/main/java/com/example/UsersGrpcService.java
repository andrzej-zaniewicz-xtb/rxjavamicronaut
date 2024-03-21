package com.example;

import helloworld.GreeterGrpc;
import helloworld.Users;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class UsersGrpcService extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(Users.HelloRequest request, StreamObserver<Users.HelloResponse> responseObserver) {
        log.info(" Start grpc");
        Users.HelloResponse response = Users.HelloResponse.newBuilder()
                .setMessage(request.getName())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
