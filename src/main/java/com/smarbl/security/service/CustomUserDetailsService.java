package com.smarbl.security.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smarbl.model.User;
import com.smarbl.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	logger.info("Attempting to load user with email: {}", email);

        Optional<User> userOptional = userRepository.findByUserEmail(email);

        if (userOptional.isEmpty()) {
            logger.warn("User not found with email: {}", email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = userOptional.get();

        
        logger.info("User found with email: {}", email);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserEmail())
                .password(user.getUserPassword())  // Ensure password is stored as bcrypt hash
               // .roles("USER")  // Assign roles, can be fetched from DB
                .build();
    }
}

//Assuming you have a list of roles assigned to the user.
// If roles are available in your database, you should fetch and assign them here.
// For now, assuming a "USER" role as a placeholder.

    /**
     *   // Return the UserDetails with roles, authorities, and password.
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserEmail())
                .password(user.getUserPassword()) // Ensure password is stored as bcrypt hash
                .authorities(user.getRoles().stream() // Fetch roles from the User entity
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toList()))
                .build();
    }**/
     
 
