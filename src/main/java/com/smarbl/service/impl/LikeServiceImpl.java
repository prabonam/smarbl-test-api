package com.smarbl.service.impl;

import com.smarbl.dto.LikeDTO;
import com.smarbl.dto.LikeCountDTO;
import com.smarbl.dto.UserDTO;
import com.smarbl.exception.PostNotFoundException;
import com.smarbl.exception.UserNotFoundException;
import com.smarbl.exception.ValidationException;
import com.smarbl.model.Like;
import com.smarbl.model.Post;
import com.smarbl.model.User;
import com.smarbl.repository.LikeRepository;
import com.smarbl.repository.PostRepository;
import com.smarbl.repository.UserRepository;
import com.smarbl.service.LikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service implementation for Like operations. Provides functionality for liking
 * posts, counting likes, and getting users who liked a post.
 */
@Service
public class LikeServiceImpl implements LikeService {

	private static final Logger logger = LoggerFactory.getLogger(LikeServiceImpl.class);

	private final LikeRepository likeRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Autowired
	public LikeServiceImpl(LikeRepository likeRepository, PostRepository postRepository,
			UserRepository userRepository) {
		this.likeRepository = likeRepository;
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	/**
	 * Like a post by a user. Validates the existence of the post and user and
	 * checks if the user has already liked the post. Logs errors and successful
	 * operations.
	 *
	 * @param likeDTO - The DTO containing the user's like details.
	 */
	@Override
	@Transactional
	public void likePost(LikeDTO likeDTO) {
		logger.info("Received request to like post with ID: {} by user with ID: {}", likeDTO.getPostId(),
				likeDTO.getUserId());

		try {
			// Check if the post exists
			Post post = postRepository.findById(likeDTO.getPostId())
					.orElseThrow(() -> new PostNotFoundException("Post not found with id: " + likeDTO.getPostId()));

			// Check if the user exists
			User user = userRepository.findById(likeDTO.getUserId())
					.orElseThrow(() -> new UserNotFoundException("User not found with id: " + likeDTO.getUserId()));

			// Prevent user from liking their own post
			if (post.getUser().getUserId().equals(user.getUserId())) {
				logger.warn("User with ID: {} tried to like their own post with ID: {}", user.getUserId(),
						post.getPostId());
				throw new ValidationException("User cannot like their own post.");
			}

			// Check if the user has already liked the post
			if (likeRepository.existsByPostAndUser(post, user)) {
				logger.warn("User with ID: {} has already liked post with ID: {}", user.getUserId(), post.getPostId());
				throw new ValidationException("User has already liked this post.");
			}

			// Create and save the like
			Like like = new Like();
			like.setPost(post);
			like.setUser(user);
			likeRepository.save(like);

			// Log successful like
			logger.info("User with ID: {} successfully liked post with ID: {}", user.getUserId(), post.getPostId());

		} catch (PostNotFoundException | UserNotFoundException | ValidationException e) {
			// Log specific exceptions
			logger.error("Error occurred while processing like for user ID: {} on post ID: {}", likeDTO.getUserId(),
					likeDTO.getPostId(), e);
			throw e; // Rethrow the exception after logging
		} catch (Exception e) {
			// Log generic errors
			logger.error("Unexpected error occurred while processing like for user ID: {} on post ID: {}",
					likeDTO.getUserId(), likeDTO.getPostId(), e);
			throw new RuntimeException("An unexpected error occurred while liking the post.", e);
		}
	}

	/**
	 * Get the count of likes for a list of posts. Logs the successful retrieval of
	 * like counts and errors.
	 *
	 * @param postIds - List of post IDs for which like counts are to be fetched
	 * @return List of LikeCountDTO with like counts for each post
	 */
	@Override
	public List<LikeCountDTO> getLikeCountsForPosts(List<UUID> postIds) {
		logger.info("Received request to get like counts for posts with IDs: {}", postIds);

		try {
			List<Post> posts = postRepository.findAllById(postIds);

			// Create LikeCountDTO for each post
			List<LikeCountDTO> likeCounts = posts.stream()
					.map(post -> new LikeCountDTO(post.getPostId(), likeRepository.countByPost(post)))
					.collect(Collectors.toList());

			// Log successful like count retrieval
			logger.info("Successfully retrieved like counts for {} posts", likeCounts.size());
			return likeCounts;
		} catch (Exception e) {
			// Log errors during like count retrieval
			logger.error("Error occurred while retrieving like counts for posts with IDs: {}", postIds, e);
			throw new RuntimeException("An error occurred while retrieving like counts.", e);
		}
	}

	/**
	 * Get the list of users who liked a specific post. Logs the successful
	 * retrieval of users and errors.
	 *
	 * @param postId - The ID of the post for which users who liked it are to be
	 *               fetched.
	 * @return List of UserDTO containing details of users who liked the post.
	 */
	@Override
	public List<UserDTO> getUsersWhoLikedPost(UUID postId) {
		logger.info("Received request to get users who liked post with ID: {}", postId);

		try {
			// Check if the post exists
			Post post = postRepository.findById(postId)
					.orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

			// Retrieve the list of likes for the post
			List<Like> likes = likeRepository.findByPost(post);

			// Map likes to UserDTO objects
			List<UserDTO> users = likes.stream().map(like -> new UserDTO(like.getUser().getUserId(),
					like.getUser().getUserName(), like.getUser().getUserEmail())).collect(Collectors.toList());

			// Log successful retrieval of users
			logger.info("Successfully retrieved {} users who liked post with ID: {}", users.size(), postId);
			return users;

		} catch (PostNotFoundException e) {
			// Log specific post not found error
			logger.error("Error occurred while retrieving users who liked post with ID: {}", postId, e);
			throw e; // Rethrow the exception after logging
		} catch (Exception e) {
			// Log any unexpected errors
			logger.error("Unexpected error occurred while retrieving users who liked post with ID: {}", postId, e);
			throw new RuntimeException("An unexpected error occurred while retrieving users who liked the post.", e);
		}
	}
}
