package com.smarbl.service;

import com.smarbl.dto.LikeDTO;
import com.smarbl.dto.LikeCountDTO;
import com.smarbl.dto.UserDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Like operations.
 */
public interface LikeService {

    void likePost(LikeDTO likeDTO);

    List<LikeCountDTO> getLikeCountsForPosts(List<UUID> postIds);

    List<UserDTO> getUsersWhoLikedPost(UUID postId);
}
