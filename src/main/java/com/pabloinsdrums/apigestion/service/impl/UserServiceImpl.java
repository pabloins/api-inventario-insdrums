package com.pabloinsdrums.apigestion.service.impl;

import com.pabloinsdrums.apigestion.dto.user.SaveUser;
import com.pabloinsdrums.apigestion.exception.InvalidPasswordException;
import com.pabloinsdrums.apigestion.model.entity.User;
import com.pabloinsdrums.apigestion.model.util.Role;
import com.pabloinsdrums.apigestion.repository.UserRepository;
import com.pabloinsdrums.apigestion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerOneCustomer(SaveUser newUser) {
        validatePassword(newUser);

        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setName(newUser.getName());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setRole(Role.ROLE_COSTUMER);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private void validatePassword(SaveUser dto) {
        if(!StringUtils.hasText(dto.getPassword()) || !StringUtils.hasText(dto.getRepeatedPassword())) {
            throw new InvalidPasswordException("Passwords don't match");
        }

        if(!dto.getPassword().equals(dto.getRepeatedPassword())) {
            throw new InvalidPasswordException("Passwords don't match");
        }
    }

}
