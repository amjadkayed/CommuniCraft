package com.communicate_craft.config;

import com.communicate_craft.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// tell it's a managed bean
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    /*
        1- check if we have JWT token
        2- extract username from header
        - call UserDetailsService to check if we have the user within our DB or not
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // all filters should be not null
        // ex. we can add header to the response here
        // filter chain: the chain of responsibility design pattern, contains the list of the other
        //               filters that we need to execute

        // authorization -> this is the header that contains JWT token or bearer token
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;


        // bearer token should always start with "Bearer " word
        // missing JWT token
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        // extract token from header
        // start after "Bearer " word
        jwt = authHeader.substring(7);

        //extract userEmail from JWT token using JwtService;
        userEmail = jwtService.extractUsername(jwt);
    }
}
