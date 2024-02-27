package com.pabloinsdrums.apigestion.controller;

import com.pabloinsdrums.apigestion.dto.auth.AuthenticationRequest;
import com.pabloinsdrums.apigestion.dto.auth.AuthenticationResponse;
import com.pabloinsdrums.apigestion.dto.auth.LogoutResponse;
import com.pabloinsdrums.apigestion.model.entity.security.User;
import com.pabloinsdrums.apigestion.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PreAuthorize("permitAll")
    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt) {
        boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest) {
        AuthenticationResponse rsp = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(rsp);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletRequest request) {
        authenticationService.logout(request);
        return ResponseEntity.ok(new LogoutResponse("Successful logout"));
    }

    @PreAuthorize("hasAuthority('READ_MY_PROFILE')")
    @GetMapping("/profile")
    public ResponseEntity<User> findMyProfile () {
        User user = authenticationService.findLoggedInUser();
        return ResponseEntity.ok(user);
    }
}
