package com.smarbl.controller;

import com.smarbl.dto.UserDTO;
import com.smarbl.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for User API.
 * Provides endpoints for managing users (CRUD operations).
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    // Logger instance for logging events and errors within the UserController
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Endpoint to create a new user.
     * Logs the input data and successful creation.
     *
     * @param userDTO - User data transfer object containing user details
     * @return ResponseEntity with created user data and HTTP status 201 (Created)
     */
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        // Log incoming request for creating a user
        logger.info("Received user creation request: {}", userDTO);

        try {
            UserDTO createdUser = userService.createUser(userDTO);
            // Log successful user creation
            logger.info("User created successfully: {}", createdUser);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log any errors that occur during user creation
            logger.error("Error occurred while creating user: {}", userDTO, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to update a user by its ID.
     * Logs the update process, including the request body and result.
     *
     * @param userId  - ID of the user to update
     * @param userDTO - Updated user details
     * @return ResponseEntity with the updated user and HTTP status 200 (OK)
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID userId, @Valid @RequestBody UserDTO userDTO) {
        // Log the request to update a user
        logger.info("Received request to update user with ID: {} and data: {}", userId, userDTO);

        try {
            UserDTO updatedUser = userService.updateUser(userId, userDTO);
            // Log successful user update
            logger.info("User updated successfully with ID: {}", userId);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            // Log any errors that occur during user update
            logger.error("Error occurred while updating user with ID: {}", userId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to delete a user by its ID.
     * Logs the request and successful deletion.
     *
     * @param userId - ID of the user to delete
     * @return ResponseEntity with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        // Log the request to delete a user
        logger.info("Received request to delete user with ID: {}", userId);

        try {
            userService.deleteUser(userId);
            // Log successful user deletion
            logger.info("User with ID: {} deleted successfully", userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Log any errors that occur during user deletion
            logger.error("Error occurred while deleting user with ID: {}", userId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to get a user by its ID.
     * Logs the request and successful retrieval.
     *
     * @param userId - ID of the user to fetch
     * @return ResponseEntity with the user data and HTTP status 200 (OK)
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID userId) {
        // Log the request to get a user by ID
        logger.info("Received request to get user with ID: {}", userId);

        try {
            UserDTO userDTO = userService.getUserById(userId);
            if (userDTO != null) {
                // Log successful retrieval of user
                logger.info("User with ID: {} retrieved successfully", userId);
                return new ResponseEntity<>(userDTO, HttpStatus.OK);
            } else {
                // Log if the user is not found
                logger.warn("User with ID: {} not found", userId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Log any errors that occur during user retrieval
            logger.error("Error occurred while retrieving user with ID: {}", userId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to get all users.
     * Logs the request and returns the list of users.
     *
     * @return ResponseEntity with the list of users and HTTP status 200 (OK)
     */
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        // Log the request to fetch all users
        logger.info("Received request to get all users");

        try {
            List<UserDTO> users = userService.getAllUsers();
            // Log successful retrieval of all users
            logger.info("Successfully retrieved {} users", users.size());
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            // Log any errors that occur during fetching all users
            logger.error("Error occurred while retrieving all users", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
