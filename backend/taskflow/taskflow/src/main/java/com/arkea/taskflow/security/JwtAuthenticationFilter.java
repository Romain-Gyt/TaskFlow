package com.arkea.taskflow.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class JwtAuthentificationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthentificationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token = null;
        if(request.getCookies() != null) {
            token = Arrays.stream(request.getCookies())
                    .filter(cookie -> "AUTH-TOKEN".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        if(token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try{
                if(!jwtService.isTokenExpired(token)) {
                    String username = jwtService.extractUsername(token);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception ex) {
                SecurityContextHolder.clearContext();
            }
        }
    }
}
