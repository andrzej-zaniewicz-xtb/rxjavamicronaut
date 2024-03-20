package com.example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Observable;

@Controller("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Get
    public Observable<User> getUsers() {
        return userService.fetchUsers()
                .onErrorReturn(throwable -> {
            // Log the error
            System.err.println("Error fetching users: " + throwable.getMessage());

            // Return an "empty" user to indicate an error
            User errorUser = new User();
            errorUser.setId("-1");
            errorUser.setName("Error");
            errorUser.setEmail("error@example.com");
            return errorUser;
        });
    }
}
