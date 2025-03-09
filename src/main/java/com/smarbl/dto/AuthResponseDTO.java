package com.smarbl.dto;

import com.smarbl.model.User;

public class AuthResponseDTO {

	private User user;
	private String jwtToken;

	public AuthResponseDTO(User user, String jwtToken) {
		this.user = user;
		this.jwtToken = jwtToken;
	}

	// Getters and setters
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
}
