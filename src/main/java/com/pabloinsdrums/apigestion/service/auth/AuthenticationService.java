package com.pabloinsdrums.apigestion.service.auth;

import com.pabloinsdrums.apigestion.dto.auth.AuthenticationRequest;
import com.pabloinsdrums.apigestion.dto.auth.AuthenticationResponse;
import com.pabloinsdrums.apigestion.dto.user.RegisteredUser;
import com.pabloinsdrums.apigestion.dto.user.SaveUser;
import com.pabloinsdrums.apigestion.exception.ObjectNotFoundException;
import com.pabloinsdrums.apigestion.model.entity.security.JwtToken;
import com.pabloinsdrums.apigestion.model.entity.security.User;
import com.pabloinsdrums.apigestion.repository.security.JwtTokenRepository;
import com.pabloinsdrums.apigestion.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JwtTokenRepository jwtRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RegisteredUser registerOneCustomer(SaveUser newUser) {
        User user = userService.registerOneCustomer(newUser);
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        saveUserToken((User) user, jwt);

        RegisteredUser userDto = new RegisteredUser();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getName());
        userDto.setRole(user.getRole().getName());
        userDto.setJwt(jwt);

        return userDto;
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name",user.getName());
        extraClaims.put("role",user.getRole().getName());
        extraClaims.put("authorities",user.getAuthorities());

        return extraClaims;
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()
        );

        authenticationManager.authenticate(authentication);

        User user = userService.findOneByUsername(authRequest.getUsername()).get();
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        saveUserToken(user, jwt);

        AuthenticationResponse authRsp = new AuthenticationResponse();
        authRsp.setJwt(jwt);

        return authRsp;
    }

    private void saveUserToken(User user, String jwt) {
        JwtToken token = new JwtToken();
        token.setToken(jwt);
        token.setUser(user);
        token.setExpiration(jwtService.extractExpiration(jwt));
        token.setValid(true);

        jwtRepository.save(token);
    }

    public boolean validateToken(String jwt) {
        try {
            jwtService.extractUsername(jwt);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public User findLoggedInUser() {
        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String username = (String) auth.getPrincipal();

        return userService.findOneByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));
    }

    public void logout(HttpServletRequest request) {
        String jwt = jwtService.extractJwtFromRequest(request);

        if(jwt == null || !StringUtils.hasText(jwt)) return;

        Optional<JwtToken> token = jwtRepository.findByToken(jwt);

        if(token.isPresent() && token.get().isValid()) {
           token.get().setValid(false);
           jwtRepository.save(token.get());
        }
    }
}
