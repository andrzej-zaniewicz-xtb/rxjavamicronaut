package com.example;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Observable;

@Client("https://jsonplaceholder.typicode.com")
public interface RemoteUserService extends UserService {

    @Get("/users")
    Observable<User> fetchUsers();
}
