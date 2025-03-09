package com.smarbl.service.impl;

import com.smarbl.dto.PostDTO;
import com.smarbl.exception.PostNotFoundException;
import com.smarbl.exception.UserNotFoundException;
import com.smarbl.model.Post;
import com.smarbl.model.User;
import com.smarbl.repository.PostRepository;
import com.smarbl.repository.UserRepository;
import com.smarbl.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation for Post operations.
 * Provides functionality for creating, retrieving, and deleting posts.
 * Also includes logic to fetch all posts by a user.
 */
@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create a new post.
     * Validates the user existence, creates a post, and logs the process.
     *
     * @param postDTO - The DTO containing the post details.
     * @return PostDTO - The DTO of the created post.
     */
    @Override
    @Transactional
    public PostDTO createPost(PostDTO postDTO) {
        logger.info("Received request to create a post for user with ID: {}", postDTO.getUserId());

        try {
            // Validate if the user exists
            User user = userRepository.findById(postDTO.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + postDTO.getUserId()));

            // Create a new post and set the details
            Post post = new Post();
            post.setPostTitle(postDTO.getPostTitle());
            post.setPostContent(postDTO.getPostContent());
            post.setUser(user);

            // Save the post to the database
            Post savedPost = postRepository.save(post);

            // Log the successful creation of the post
            logger.info("Successfully created post with ID: {} for user with ID: {}", savedPost.getPostId(), user.getUserId());

            // Return the created post as a DTO
            return new PostDTO(savedPost.getPostId(), savedPost.getPostTitle(), savedPost.getPostContent(), user.getUserId());

        } catch (UserNotFoundException e) {
            // Log the exception and rethrow
            logger.error("Error creating post: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            // Log any unexpected exceptions
            logger.error("Unexpected error occurred while creating post", e);
            throw new RuntimeException("An unexpected error occurred while creating the post", e);
        }
    }

    /**
     * Delete a post by its ID.
     * Logs the deletion request and ensures the post exists before deleting.
     *
     * @param postId - The ID of the post to be deleted.
     */
    @Override
    public void deletePost(UUID postId) {
        logger.info("Received request to delete post with ID: {}", postId);

        try {
            // Check if the post exists
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

            // Delete the post
            postRepository.delete(post);

            // Log the successful deletion
            logger.info("Successfully deleted post with ID: {}", postId);

        } catch (PostNotFoundException e) {
            // Log the exception and rethrow
            logger.error("Error deleting post: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            // Log any unexpected exceptions
            logger.error("Unexpected error occurred while deleting post with ID: {}", postId, e);
            throw new RuntimeException("An unexpected error occurred while deleting the post", e);
        }
    }

    /**
     * Get a post by its ID.
     * Logs the retrieval request and ensures the post exists.
     *
     * @param postId - The ID of the post to retrieve.
     * @return PostDTO - The DTO of the retrieved post.
     */
    @Override
    public PostDTO getPostById(UUID postId) {
        logger.info("Received request to get post with ID: {}", postId);

        try {
            // Retrieve the post by ID
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

            // Log the successful retrieval
            logger.info("Successfully retrieved post with ID: {}", postId);

            // Return the post as a DTO
            return new PostDTO(post.getPostId(), post.getPostTitle(), post.getPostContent(), post.getUser().getUserId());

        } catch (PostNotFoundException e) {
            // Log the exception and rethrow
            logger.error("Error retrieving post: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            // Log any unexpected exceptions
            logger.error("Unexpected error occurred while retrieving post with ID: {}", postId, e);
            throw new RuntimeException("An unexpected error occurred while retrieving the post", e);
        }
    }

    /**
     * Get all posts by a specific user.
     * Logs the request and ensures the user exists before fetching the posts.
     *
     * @param userId - The ID of the user for whom to retrieve posts.
     * @return List<PostDTO> - List of posts for the user.
     */
    @Override
    public List<PostDTO> getAllPostsByUser(UUID userId) {
        logger.info("Received request to get all posts for user with ID: {}", userId);

        try {
            // Ensure the user exists
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

            // Fetch all posts by the user
            List<Post> posts = postRepository.findByUser(user);

            // Log the successful retrieval of posts
            logger.info("Successfully retrieved {} posts for user with ID: {}", posts.size(), userId);

            // Map posts to PostDTO and return the list
            return posts.stream()
                    .map(post -> new PostDTO(post.getPostId(), post.getPostTitle(), post.getPostContent(), userId))
                    .collect(Collectors.toList());

        } catch (UserNotFoundException e) {
            // Log the exception and rethrow
            logger.error("Error retrieving posts for user: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            // Log any unexpected exceptions
            logger.error("Unexpected error occurred while retrieving posts for user with ID: {}", userId, e);
            throw new RuntimeException("An unexpected error occurred while retrieving posts for the user", e);
        }
    }
}
