package com.pabloinsdrums.apigestion.repository.security;

import com.pabloinsdrums.apigestion.model.entity.security.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByToken(String jwt);
}
