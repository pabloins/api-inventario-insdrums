package com.pabloinsdrums.apigestion.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement( sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                    .authenticationProvider(daoAuthProvider)
                    .authorizeHttpRequests( authReqConfig -> {
                        authReqConfig.requestMatchers(HttpMethod.POST,"/customers").permitAll();
                        authReqConfig.requestMatchers(HttpMethod.POST,"/auth/**").permitAll();

                        authReqConfig.anyRequest().authenticated();
                    } )
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
