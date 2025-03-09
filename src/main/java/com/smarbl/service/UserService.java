package com.smarbl.service;

import com.smarbl.dto.UserDTO;
import com.smarbl.model.User;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing users.
 */
public interface UserService {

	/**
	 * Creates a new user.
	 *
	 * @param userDTO the user data transfer object
	 * @return the created user DTO
	 */
	UserDTO createUser(UserDTO userDTO);

	/**
	 * Updates an existing user.
	 *
	 * @param userId  the ID of the user to be updated
	 * @param userDTO the user data transfer object
	 * @return the updated user DTO
	 */
	UserDTO updateUser(UUID userId, UserDTO userDTO);

	/**
	 * Deletes a user by ID.
	 *
	 * @param userId the ID of the user to be deleted
	 */
	void deleteUser(UUID userId);

	/**
	 * Retrieves a user by ID.
	 *
	 * @param userId the ID of the user
	 * @return the user DTO
	 */
	UserDTO getUserById(UUID userId);

	/**
	 * Retrieves all users.
	 *
	 * @return a list of user DTOs
	 */
	List<UserDTO> getAllUsers();

}
