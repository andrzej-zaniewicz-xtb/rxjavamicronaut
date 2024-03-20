package com.example;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
@NoArgsConstructor
public class RemoteUserService implements UserService {

    @Inject
    private HttpClient client;

    public Flux<User> fetchUsers() {
        HttpRequest<?> req = HttpRequest.GET("https://jsonplaceholder.typicode.com/users");
        return Flux
                .from(client.retrieve(req, Argument.listOf(User.class)))
                .flatMap(Flux::fromIterable);
    }

}
