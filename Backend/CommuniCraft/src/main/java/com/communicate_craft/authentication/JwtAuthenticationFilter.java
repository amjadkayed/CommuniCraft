package com.communicate_craft.authentication;

import com.communicate_craft.authentication.JwtService;
import com.communicate_craft.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
// tell it's a managed bean
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /*
        1- check if we have JWT token
        2- extract username from header
        - call UserDetailsService to check if we have the user within our DB or not
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtAuthenticationFilter --> doFilterInternal");
        // all filters should be not null
        // ex. we can add header to the response here
        // filter chain: the chain of responsibility design pattern, contains the list of the other
        //               filters that we need to execute

        // authorization -> this is the header that contains JWT token or bearer token
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        log.info("JwtAuthenticationFilter --> doFilterInternal --> auth header = " + authHeader);

        // bearer token should always start with "Bearer " word
        // missing JWT token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("JwtAuthenticationFilter --> doFilterInternal --> token doesn't exist");
            filterChain.doFilter(request, response);
            return;
        }

        // extract token from header
        // start after "Bearer " word
        jwt = authHeader.substring(7);
        log.info("JwtAuthenticationFilter --> doFilterInternal --> token: " + jwt);

        // extract userEmail from JWT token using JwtService
        try {
            userEmail = jwtService.extractUsername(jwt);
            log.info("JwtAuthenticationFilter --> doFilterInternal --> userEmail: " + userEmail);
            // check if the user is already authenticated
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // the user is not authenticated
                User userDetails = (User) this.userDetailsService.loadUserByUsername(userEmail);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    log.info("JwtAuthenticationFilter --> doFilterInternal --> the token is valid");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.write("{\"error\": \""+e.getMessage()+"\"}");
            writer.flush();
            log.error("JwtAuthenticationFilter --> doFilterInternal --> invalid token", e);
            return;
        }


        filterChain.doFilter(request, response);
    }
}
