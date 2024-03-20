package com.example;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Flux;

@Client("https://jsonplaceholder.typicode.com")
public interface RemoteUserService extends UserService {

    @Get("/users")
    Flux<User> fetchUsers();
}
