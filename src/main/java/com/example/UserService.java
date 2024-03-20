package com.example;
;
import io.reactivex.Observable;

public interface UserService {
    Observable<User> fetchUsers();
}
