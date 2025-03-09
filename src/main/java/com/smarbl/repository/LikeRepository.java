package com.smarbl.repository;

import com.smarbl.model.Like;
import com.smarbl.model.Post;
import com.smarbl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Like entity.
 */
@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {

    /**
     * Checks if a user has already liked a post.
     */
    boolean existsByPostAndUser(Post post, User user);

    /**
     * Counts the number of likes for a given post.
     */
    long countByPost(Post post);

    /**
     * Finds all likes for a given post.
     */
    List<Like> findByPost(Post post);

    /**
     * Finds the count of likes for a list of posts.
     */
    List<Like> findByPostIn(List<Post> posts);
}
