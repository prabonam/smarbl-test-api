package com.smarbl.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Data Transfer Object for User.
 */
public class UserDTO {

    private UUID userId;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 255, message = "Username cannot exceed 255 characters")
    private String userName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String userEmail;
    
 
    @JsonIgnore  // Prevent password from being exposed in API responses
    private String userPassword;

    // Default constructor
    public UserDTO() {
    }

    // Constructor for easy creation
    public UserDTO(UUID userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
