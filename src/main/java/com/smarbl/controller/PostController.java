 package com.smarbl.controller;

import com.smarbl.dto.PostDTO;
import com.smarbl.service.PostService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Post API.
 * Provides endpoints for managing blog posts (create, read, delete, and list posts by user).
 */
@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    // Logger instance to log messages related to the PostController
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    // Constructor-based dependency injection for PostService
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Endpoint to create a new post.
     * Logs the input data and any potential errors during the process.
     *
     * @param postDTO - Post data transfer object containing post details
     * @return ResponseEntity with created post data and HTTP status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        logger.info("Received request to create a post with data: {}", postDTO);
        
        try {
            PostDTO createdPost = postService.createPost(postDTO);
            logger.info("Post created successfully with data: {}", createdPost);
            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the error with exception stack trace
            logger.error("Error occurred while creating post with data: {}", postDTO, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to delete a post by its ID.
     * Logs the post deletion process, including any errors.
     *
     * @param postId - ID of the post to be deleted
     * @return ResponseEntity with HTTP status 204 (No Content) if deletion is successful
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId) {
        logger.info("Received request to delete post with ID: {}", postId);

        try {
            postService.deletePost(postId);
            logger.info("Post with ID: {} deleted successfully", postId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Log the error with exception stack trace
            logger.error("Error occurred while deleting post with ID: {}", postId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to fetch a post by its ID.
     * Logs the process of fetching a post and any related errors.
     *
     * @param postId - ID of the post to retrieve
     * @return ResponseEntity with the requested post data and HTTP status 200 (OK)
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable UUID postId) {
        logger.info("Received request to get post with ID: {}", postId);

        try {
            PostDTO postDTO = postService.getPostById(postId);
            if (postDTO != null) {
                logger.info("Post with ID: {} found and returned", postId);
                return ResponseEntity.ok(postDTO);
            } else {
                logger.warn("Post with ID: {} not found", postId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the error with exception stack trace
            logger.error("Error occurred while retrieving post with ID: {}", postId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to fetch all posts by a specific user.
     * Logs the process of fetching posts for a user and any errors encountered.
     *
     * @param userId - ID of the user whose posts need to be fetched
     * @return ResponseEntity with the list of posts and HTTP status 200 (OK)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getAllPostsByUser(@PathVariable UUID userId) {
        logger.info("Received request to get all posts for user with ID: {}", userId);

        try {
            List<PostDTO> posts = postService.getAllPostsByUser(userId);
            logger.info("Successfully fetched {} posts for user with ID: {}", posts.size(), userId);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            // Log the error with exception stack trace
            logger.error("Error occurred while fetching posts for user with ID: {}", userId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
