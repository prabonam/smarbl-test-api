/**
 * 
 */
package com.smarbl.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.UUID;
import java.util.Set;

@Entity
@Table(name = "post_tbl")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "post_id", updatable = false, nullable = false)
	private UUID postId;

	@NotBlank(message = "Title cannot be blank")
	@Size(max = 255, message = "Title cannot exceed 255 characters")
	@Column(name = "post_title", nullable = false)
	private String postTitle;

	@NotBlank(message = "Content cannot be blank")
	@Column(name = "post_content", nullable = false)
	private String postContent;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "post")
	private Set<Like> likes;

	/**
	 * @return the postId
	 */
	public UUID getPostId() {
		return postId;
	}

	/**
	 * @return the postTitle
	 */
	public String getPostTitle() {
		return postTitle;
	}

	/**
	 * @return the postContent
	 */
	public String getPostContent() {
		return postContent;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the likes
	 */
	public Set<Like> getLikes() {
		return likes;
	}

	/**
	 * @param postId the postId to set
	 */
	public void setPostId(UUID postId) {
		this.postId = postId;
	}

	/**
	 * @param postTitle the postTitle to set
	 */
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	/**
	 * @param postContent the postContent to set
	 */
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikes(Set<Like> likes) {
		this.likes = likes;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", postTitle=" + postTitle + ", postContent=" + postContent + ", user=" + user
				+ ", likes=" + likes + "]";
	}
}
