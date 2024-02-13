package com.pabloinsdrums.apigestion.service;

import com.pabloinsdrums.apigestion.dto.user.SaveUser;
import com.pabloinsdrums.apigestion.model.entity.User;

import java.util.Optional;

public interface UserService {
    User registerOneCustomer(SaveUser newUser);

    Optional<User> findOneByUsername(String username);
}
