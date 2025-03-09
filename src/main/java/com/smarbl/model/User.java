/**
 User - Post: A User can have multiple Posts. This is a One-to-Many relationship. 
 * The User entity has a Set<Post> field, and the Post entity has a ManyToOne relation to User.
 * 
 * User - Like: A User can like many posts, and each like is associated with a user. 
 * This is a One-to-Many relationship. The Like entity has a ManyToOne relation to User.
 * 
 */
package com.smarbl.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user_tbl")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Consider UUID generation strategy
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 255, message = "Username cannot exceed 255 characters")
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @NotBlank(message = "Password cannot be blank")
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    // Initialize Sets to avoid NullPointerExceptions
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();   
    
    @OneToMany(mappedBy = "user", cascade = { CascadeType.ALL}, orphanRemoval = true)
    private Set<Like> likes = new HashSet<>();   

    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail + ", userPassword="
                + userPassword + ", posts=" + posts + ", likes=" + likes + "]";
    }
}

