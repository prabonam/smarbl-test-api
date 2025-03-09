package com.smarbl.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smarbl.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWT Authentication Filter to intercept requests and validate JWT tokens.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    // Constructor to inject dependencies
    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Extracts JWT token, validates it, and sets authentication in the security context.
     * This method will be called for each request to check if the JWT token is valid.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract Authorization header from the request
        final String authorizationHeader = request.getHeader("Authorization");

        // If the header is missing or does not start with "Bearer ", pass the request along the filter chain
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.debug("Authorization header is missing or does not start with 'Bearer '");
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token from the Authorization header
        String jwtToken = authorizationHeader.substring(7);
        String email = null;

        // Try to extract the email from the JWT token
        try {
            email = jwtUtil.extractEmail(jwtToken);
        } catch (ExpiredJwtException e) {
            logger.error("JWT token has expired: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired");
            return;
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
            return;
        } catch (IllegalArgumentException e) {
            logger.error("Invalid token format: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid token format");
            return;
        }

        // If email is valid and user is not already authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details by email
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // If the token is valid, set authentication in the SecurityContext
            if (jwtUtil.validateToken(jwtToken, userDetails.getUsername())) {
                logger.info("JWT token is valid for user: {}", email);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                logger.warn("JWT token validation failed for user: {}", email);
            }
        }

        // Pass the request along the filter chain
        filterChain.doFilter(request, response);
    }
}
