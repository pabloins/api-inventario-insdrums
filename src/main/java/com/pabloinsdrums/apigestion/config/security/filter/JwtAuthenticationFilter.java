package com.pabloinsdrums.apigestion.config.security.filter;

import com.pabloinsdrums.apigestion.exception.ObjectNotFoundException;
import com.pabloinsdrums.apigestion.model.entity.security.JwtToken;
import com.pabloinsdrums.apigestion.model.entity.security.User;
import com.pabloinsdrums.apigestion.repository.security.JwtTokenRepository;
import com.pabloinsdrums.apigestion.service.UserService;
import com.pabloinsdrums.apigestion.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JwtTokenRepository jwtRepository;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. get header authorization
        // 2. get token
        String jwt = jwtService.extractJwtFromRequest(request);
        if(!StringUtils.hasText(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2.1 get non-expired and valid token from database
        Optional<JwtToken> token = jwtRepository.findByToken(jwt);
        boolean isValid = validateToken(token);

        if(!isValid) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. get subject/username from token
        // this action in turn validates the token format, signature and expiry date.
        String username = jwtService.extractUsername(jwt);

        // 3.1 validate token expiration

        // 4. set authentication object
        User user = userService.findOneByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, user.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 5. run the filter register
        filterChain.doFilter(request, response);
    }

    private boolean validateToken(Optional<JwtToken> optionalJwtToken) {
        if(!optionalJwtToken.isPresent()) {
            System.out.println("Token does not exist or was not generated");
            return false;
        }

        JwtToken token = optionalJwtToken.get();
        Date now = new Date(System.currentTimeMillis());

        boolean isValid = token.isValid() && token.getExpiration().after(now);

        if(!isValid) {
            System.out.println("Invalid token");
            updateTokenStatus(token);
        }

        return isValid;
    }

    private void updateTokenStatus(JwtToken token) {
        token.setValid(false);
        jwtRepository.save(token);
    }
}
