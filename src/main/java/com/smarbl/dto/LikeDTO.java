package com.smarbl.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeDTO {

	/**
	 * Data Transfer Object for Like operations.
	 */

	@NotNull(message = "Post ID cannot be null")
	private UUID postId; // Make sure this field is present

	@NotNull(message = "User ID cannot be null")
	private UUID userId; // Make sure this field is present

	private UUID likeId;
	private String userName; // Simplified user data
	private String postTitle; // Simplified post data

	// Default constructor
	public LikeDTO() {
	}

	// Constructor for easy creation
	public LikeDTO(UUID likeId, String userName, String postTitle) {
		this.likeId = likeId;
		this.userName = userName;
		this.postTitle = postTitle;
	}

	// Getters and Setters
	public UUID getLikeId() {
		return likeId;
	}

	public void setLikeId(UUID likeId) {
		this.likeId = likeId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	/**
	 * @return the postId
	 */
	public UUID getPostId() {
		return postId;
	}

	/**
	 * @return the userId
	 */
	public UUID getUserId() {
		return userId;
	}

	/**
	 * @param postId the postId to set
	 */
	public void setPostId(UUID postId) {
		this.postId = postId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "LikeDTO{" + "likeId=" + likeId + ", userName='" + userName + '\'' + ", postTitle='" + postTitle + '\''
				+ '}';
	}
}
