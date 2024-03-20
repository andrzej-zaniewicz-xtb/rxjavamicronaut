package com.example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Controller("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Get
    public Flux<User> getUsers() {
        log.info(" Start servcie");
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
                });
    }
}
