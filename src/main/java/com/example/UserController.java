package com.example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Controller("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Get
    public Observable<User> getUsers() {
        log.info(" Start servcie");
        return userService.fetchUsers()
                .doOnNext(user -> log.info(" Fetched user: " + user.getName()))
                .onErrorReturn(throwable -> {
                    // Log the error
                    log.error("Error fetching users: " + throwable.getMessage());

                    // Return an "empty" user to indicate an error
                    User errorUser = new User();
                    errorUser.setId("-1");
                    errorUser.setName("Error");
                    errorUser.setEmail("error@example.com");
                    return errorUser;
                });
    }
}
