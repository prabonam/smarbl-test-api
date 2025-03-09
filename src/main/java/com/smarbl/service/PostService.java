package com.smarbl.service;

import com.smarbl.dto.PostDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Post operations.
 */
public interface PostService {

    PostDTO createPost(PostDTO postDTO);

    void deletePost(UUID postId);

    PostDTO getPostById(UUID postId);

    List<PostDTO> getAllPostsByUser(UUID userId);
}
