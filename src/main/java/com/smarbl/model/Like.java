package com.smarbl.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "like_tbl")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "like_id", updatable = false, nullable = false)
    private UUID likeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

	/**
	 * @return the likeId
	 */
	public UUID getLikeId() {
		return likeId;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the post
	 */
	public Post getPost() {
		return post;
	}

	/**
	 * @param likeId the likeId to set
	 */
	public void setLikeId(UUID likeId) {
		this.likeId = likeId;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(Post post) {
		this.post = post;
	}

    // Getters and Setters
}
