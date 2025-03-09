package com.smarbl.service;

import com.smarbl.model.User;

/**
 * The interface defines two methods: authenticate(): For authenticating the
 * user based on email and password. registerUser(): For registering a new user.
 */

 

public interface UserAuthenticateService {

	/**
	 * Authenticate a user based on email and password.
	 * 
	 * @param email    - User's email.
	 * @param password - User's password.
	 * @return - User object if authenticated, else null.
	 */
	 String authenticateUser(String email, String password);

	/**
	@Override
	public String authenticateUser(String email, String password) {
	    // Find the user by email
	    Optional<User> optionalUser = userRepository.findByUserEmail(email);
	    User user = optionalUser.orElseThrow(() -> new InvalidCredentialsException("Invalid email or password."));
	
	    // Validate the password
	    if (passwordEncoder.matches(password, user.getUserPassword())) {
	        return jwtUtil.generateToken(user.getUserEmail());  // ✅ Use instance method
	    } else {
	        throw new InvalidCredentialsException("Invalid email or password.");
	    }
	}
	
	
	/**
	 * Register a new user.
	 * @param user - User object to be registered.
	 * @return - The saved User object.
	 */
	User registerUser(User user);

	/**
	 * Register a new user.
	 * 
	 * @param user - User object to be registered.
	 * @return - The saved User object.
	 */
//	User registerUser(User user);

	/**
	 * Authenticate a user based on email and password.
	 * 
	 * @param email    - User's email.
	 * @param password - User's password.
	 * @return - JWT token if authenticated, else throw exception.
	 */
	//String authenticate(String email, String password); // Return type should be String (JWT token)

	/**
	 * Register a new user.
	 * 
	 * @param user - User object to be registered.
	 * @return - The saved User object.
	 */
///	User registerUser(User user);

}
