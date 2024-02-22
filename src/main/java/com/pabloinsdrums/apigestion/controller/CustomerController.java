package com.pabloinsdrums.apigestion.controller;

import com.pabloinsdrums.apigestion.dto.user.RegisteredUser;
import com.pabloinsdrums.apigestion.dto.user.SaveUser;
import com.pabloinsdrums.apigestion.model.entity.security.User;
import com.pabloinsdrums.apigestion.service.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private AuthenticationService authenticationService;

    @PreAuthorize("permitAll")
    @PostMapping
    public ResponseEntity<RegisteredUser> registerOne(@RequestBody @Valid SaveUser newUser) {
        RegisteredUser registeredUser = authenticationService.registerOneCustomer(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    //test endpoint denied for all users
    @PreAuthorize("denyAll")
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(List.of());
    }
}
