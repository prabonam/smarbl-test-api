package com.smarbl.service;

import com.smarbl.exception.InvalidCredentialsException;
import com.smarbl.model.User;
import com.smarbl.repository.UserRepository;
import com.smarbl.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthenticateServiceImpl implements UserAuthenticateService {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticateServiceImpl.class);

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthenticateServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Using BCrypt for password encoding
    }

    /**
     * Authenticate a user based on email and password.
     * @param email - User's email.
     * @param password - User's password.
     * @return - JWT token if authenticated, else throw InvalidCredentialsException.
     */
    @Override
    public String authenticateUser(String email, String password) {
        // Log the start of the authentication process
        logger.info("Attempting to authenticate user with email: {}", email);

        // Find the user by email
        Optional<User> optionalUser = userRepository.findByUserEmail(email);
        User user = optionalUser.orElseThrow(() -> {
            logger.error("Authentication failed: User not found with email: {}", email);
            return new InvalidCredentialsException("Invalid email or password.");
        });

        // Check if the provided password matches the stored one
        if (passwordEncoder.matches(password, user.getUserPassword())) {
            logger.info("Authentication successful for user: {}", email);
            return jwtUtil.generateToken(user.getUserEmail());
        } else {
            logger.error("Authentication failed: Invalid password for email: {}", email);
            throw new InvalidCredentialsException("Invalid email or password.");
        }
    }

    /**
     * Register a new user.
     * @param user - User object to be registered.
     * @return - The saved User object.
     */
    @Override
    public User registerUser(User user) {
        // Log the registration process
        logger.info("Attempting to register user with email: {}", user.getUserEmail());

        // Encrypt the password before saving
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        
        // Save the user and return
        User savedUser = userRepository.save(user);
        logger.info("User registered successfully with email: {}", savedUser.getUserEmail());
        return savedUser;
    }
}
