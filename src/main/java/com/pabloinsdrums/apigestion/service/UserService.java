package com.pabloinsdrums.apigestion.service;

import com.pabloinsdrums.apigestion.dto.SaveUser;
import com.pabloinsdrums.apigestion.model.entity.User;

public interface UserService {
    User registerOneCustomer(SaveUser newUser);
}
