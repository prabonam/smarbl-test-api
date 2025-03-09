package com.smarbl.service.impl;

import com.smarbl.dto.UserDTO;
import com.smarbl.exception.UserNotFoundException;
import com.smarbl.model.User;
import com.smarbl.repository.UserRepository;
import com.smarbl.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation for managing users.
 * Handles CRUD operations related to users.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     * Creates a new user and logs the process.
     *
     * @param userDTO The DTO containing user details.
     * @return UserDTO The DTO of the created user.
     */
    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        logger.info("Received request to create a user with username: {}", userDTO.getUserName());

        try {
            // Create a new user object
            User user = new User();
            user.setUserId(UUID.randomUUID());  // Generate a new UUID for the user
            user.setUserName(userDTO.getUserName());
            user.setUserEmail(userDTO.getUserEmail());
            user.setUserPassword(userDTO.getUserPassword());  // Hash password in production

            // Save the user to the database
            User savedUser = userRepository.save(user);

            logger.info("Successfully created user with ID: {}", savedUser.getUserId());

            // Return the created user as a DTO
            return new UserDTO(savedUser.getUserId(), savedUser.getUserName(), savedUser.getUserEmail());

        } catch (Exception e) {
            logger.error("Unexpected error occurred while creating user", e);
            throw new RuntimeException("An unexpected error occurred while creating the user", e);
        }
    }

    /**
     * {@inheritDoc}
     * Updates an existing user and logs the process.
     *
     * @param userId  The ID of the user to update.
     * @param userDTO The DTO containing updated user details.
     * @return UserDTO The DTO of the updated user.
     */
    @Override
    @Transactional
    public UserDTO updateUser(UUID userId, UserDTO userDTO) {
        logger.info("Received request to update user with ID: {}", userId);

        try {
            // Retrieve the existing user from the database
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

            // Update the user's details
            existingUser.setUserName(userDTO.getUserName());
            existingUser.setUserEmail(userDTO.getUserEmail());
            existingUser.setUserPassword(userDTO.getUserPassword());  // Hash password in production

            // Save the updated user
            User updatedUser = userRepository.save(existingUser);

            logger.info("Successfully updated user with ID: {}", updatedUser.getUserId());

            // Return the updated user as a DTO
            return new UserDTO(updatedUser.getUserId(), updatedUser.getUserName(), updatedUser.getUserEmail());

        } catch (UserNotFoundException e) {
            logger.error("Error updating user: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while updating user with ID: {}", userId, e);
            throw new RuntimeException("An unexpected error occurred while updating the user", e);
        }
    }

    /**
     * {@inheritDoc}
     * Deletes a user and logs the process.
     *
     * @param userId The ID of the user to delete.
     */
    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        logger.info("Received request to delete user with ID: {}", userId);

        try {
            // Retrieve the existing user from the database
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

            // Delete the user from the database
            userRepository.delete(existingUser);

            logger.info("Successfully deleted user with ID: {}", userId);

        } catch (UserNotFoundException e) {
            logger.error("Error deleting user: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while deleting user with ID: {}", userId, e);
            throw new RuntimeException("An unexpected error occurred while deleting the user", e);
        }
    }

    /**
     * {@inheritDoc}
     * Retrieves a user by ID and logs the process.
     *
     * @param userId The ID of the user to retrieve.
     * @return UserDTO The DTO of the user.
     */
    @Override
    public UserDTO getUserById(UUID userId) {
        logger.info("Received request to get user with ID: {}", userId);

        try {
            // Retrieve the user by ID from the database
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

            logger.info("Successfully retrieved user with ID: {}", userId);

            // Return the user as a DTO
            return new UserDTO(existingUser.getUserId(), existingUser.getUserName(), existingUser.getUserEmail());

        } catch (UserNotFoundException e) {
            logger.error("Error retrieving user: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving user with ID: {}", userId, e);
            throw new RuntimeException("An unexpected error occurred while retrieving the user", e);
        }
    }

    /**
     * {@inheritDoc}
     * Retrieves all users and logs the process.
     *
     * @return List<UserDTO> List of all users.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        logger.info("Received request to get all users");

        try {
            // Retrieve all users from the database
            List<User> users = userRepository.findAll();

            logger.info("Successfully retrieved {} users", users.size());

            // Map users to UserDTO and return the list
            return users.stream()
                    .map(user -> new UserDTO(user.getUserId(), user.getUserName(), user.getUserEmail()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving all users", e);
            throw new RuntimeException("An unexpected error occurred while retrieving the users", e);
        }
    }
}
