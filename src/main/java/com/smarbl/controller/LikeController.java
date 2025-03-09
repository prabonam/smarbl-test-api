package com.smarbl.controller;

import com.smarbl.dto.LikeDTO;
import com.smarbl.dto.LikeCountDTO;
import com.smarbl.dto.UserDTO;
import com.smarbl.service.LikeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Like API.
 */
@RestController
@RequestMapping("/api/v1/like")
public class LikeController {

    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<Void> likePost(@Valid @RequestBody LikeDTO likeDTO) {
        logger.info("Received request to like post with data: {}", likeDTO);
        try {
            likeService.likePost(likeDTO);
            logger.info("Post liked successfully with data: {}", likeDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while liking post with data: {}", likeDTO, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<List<LikeCountDTO>> getLikeCounts(@RequestParam List<UUID> postIds) {
        logger.info("Received request to get like counts for posts with IDs: {}", postIds);
        try {
            List<LikeCountDTO> likeCounts = likeService.getLikeCountsForPosts(postIds);
            logger.info("Successfully fetched like counts for posts: {}", postIds);
            return ResponseEntity.ok(likeCounts);
        } catch (Exception e) {
            logger.error("Error occurred while fetching like counts for posts: {}", postIds, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{postId}")
    public ResponseEntity<List<UserDTO>> getUsersWhoLikedPost(@PathVariable UUID postId) {
        logger.info("Received request to get users who liked the post with ID: {}", postId);
        try {
            List<UserDTO> usersWhoLiked = likeService.getUsersWhoLikedPost(postId);
            logger.info("Successfully fetched users who liked post with ID: {}", postId);
            return ResponseEntity.ok(usersWhoLiked);
        } catch (Exception e) {
            logger.error("Error occurred while fetching users who liked post with ID: {}", postId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
