package com.smarbl.repository;

import com.smarbl.model.Post;
import com.smarbl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Post entity.
 * UUID ensures uniqueness and security.

 */
@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    /**
     * Finds all posts by a specific user.
     *
     * @param user the user entity
     * @return list of posts
     */
    List<Post> findByUser(User user);
}




