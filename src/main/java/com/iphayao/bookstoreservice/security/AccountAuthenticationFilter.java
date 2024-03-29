package com.iphayao.bookstoreservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class AccountAuthenticationFilter extends BasicAuthenticationFilter {
    private ObjectMapper objectMapper = new ObjectMapper();
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;
    private RememberMeServices rememberMeServices = new NullRememberMeServices();
    private AuthenticationEntryPoint authenticationEntryPoint = new BasicAuthenticationEntryPoint();

    public AccountAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationDetailsSource = new WebAuthenticationDetailsSource();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            UsernamePasswordAuthenticationToken authRequest = convert(request);
            if (authRequest == null) {
                chain.doFilter(request, response);
                return;
            }

            String userName = authRequest.getName();

            if (authenticationIsRequired(userName)) {
                Authentication authResult = getAuthenticationManager().authenticate(authRequest);
                log.info("Login '{}' success", userName);

                SecurityContextHolder.getContext().setAuthentication(authResult);
                rememberMeServices.loginSuccess(request, response, authResult);

                onSuccessfulAuthentication(request, response, authResult);
            }
        } catch (AuthenticationException failed) {
            log.info("Login failed: {}", failed.getMessage());

            SecurityContextHolder.clearContext();
            rememberMeServices.loginFail(request, response);

            onUnsuccessfulAuthentication(request, response, failed);

            authenticationEntryPoint.commence(request, response, failed);

            return;
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken convert(HttpServletRequest request) throws IOException {
        if (request.getMethod().equalsIgnoreCase("POST") && request.getRequestURI().equalsIgnoreCase("/login")) {
            Map body = objectMapper.readValue(request.getInputStream(), Map.class);
            if (body.size() == 2 && body.containsKey("username") && body.containsKey("password")) {
                String principal = (String) body.get("username");
                String credential = (String) body.get("password");

                UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal, credential);
                result.setDetails(authenticationDetailsSource.buildDetails(request));
                return result;
            } else {
                throw new BadCredentialsException("Incorrect request body");
            }
        }

        return null;
    }

    private boolean authenticationIsRequired(String username) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();

        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }

        if (existingAuth instanceof UsernamePasswordAuthenticationToken && !existingAuth.getName().equalsIgnoreCase(username)) {
            return true;
        }

        return existingAuth instanceof AnonymousAuthenticationToken;
    }
}