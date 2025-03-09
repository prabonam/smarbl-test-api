package com.smarbl.security.config.contoller;

import com.smarbl.dto.LoginRequestDTO;
import com.smarbl.model.User;
import com.smarbl.service.UserAuthenticateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserLoginController {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    private UserAuthenticateService userAuthenticateService;

    /**
     * Endpoint to login a user.
     * 
     * @param loginRequest Login request body containing email and password.
     * @return JWT token if authentication is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        try {
            logger.info("Attempting to authenticate user with email: {}", loginRequest.getEmail());
            String token = userAuthenticateService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());

            // Return token if authentication is successful
            if (token != null) {
                logger.info("User authenticated successfully with email: {}", loginRequest.getEmail());
                return ResponseEntity.ok().body(token);
            } else {
                logger.warn("Authentication failed for email: {}", loginRequest.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                     .body("Invalid credentials, please try again.");
            }
        } catch (Exception e) {
            logger.error("Error during authentication for email: {}", loginRequest.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred while processing the login.");
        }
    }

    /**
     * Endpoint to register a new user.
     * 
     * @param user The user object containing registration information.
     * @return Response with the registered user details.
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            logger.info("Attempting to register new user with email: {}", user.getUserEmail());
            User registeredUser = userAuthenticateService.registerUser(user);

            logger.info("User registered successfully with email: {}", user.getUserEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (Exception e) {
            logger.error("Error during user registration with email: {}", user.getUserEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(null);
        }
    }
}
