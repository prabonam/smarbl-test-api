package com.smarbl.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for returning like counts.
 */

@Data

//@AllArgsConstructor // This will generate a constructor with both postId and likeCount
public class LikeCountDTO {
	private UUID postId;
	private long likeCount;

	// Constructor is automatically generated by @AllArgsConstructor

	/**
	 * DTO for returning like counts.
	 */

	// Explicit constructor
	public LikeCountDTO(UUID postId, long likeCount) {
		this.postId = postId;
		this.likeCount = likeCount;
	}

	/**
	 * @return the postId
	 */
	public UUID getPostId() {
		return postId;
	}

	/**
	 * @return the likeCount
	 */
	public long getLikeCount() {
		return likeCount;
	}

	/**
	 * @param postId the postId to set
	 */
	public void setPostId(UUID postId) {
		this.postId = postId;
	}

	/**
	 * @param likeCount the likeCount to set
	 */
	public void setLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}
}
