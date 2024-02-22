package com.pabloinsdrums.apigestion.config.security.authorization;

import com.pabloinsdrums.apigestion.model.entity.security.Operation;
import com.pabloinsdrums.apigestion.repository.security.OperationRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       RequestAuthorizationContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();

        String url = extractUrl(request);
        String httpMethod = request.getMethod();

        boolean isPublic = isPublic(url, httpMethod);

        return new AuthorizationDecision(true);
    }

    private boolean isPublic(String url, String httpMethod){
        List<Operation> publicAccessEndpoints = operationRepository.findByPublicAccess();

        return publicAccessEndpoints.stream().anyMatch(operation -> {
            String basePath = operation.getModule().getBasePath();
            Pattern pattern = Pattern.compile(basePath.concat(operation.getPath()));
            Matcher matcher = pattern.matcher(url);

            return matcher.matches() && operation.getHttpMethod().equals(httpMethod);
        } );
    }

    private String extractUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String url = request.getRequestURI();
        url = url.replace(contextPath, "");

        return url;
    }
}
