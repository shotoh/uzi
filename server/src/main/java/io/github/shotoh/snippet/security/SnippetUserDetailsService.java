package io.github.shotoh.snippet.security;

import io.github.shotoh.snippet.exceptions.ResourceNotFoundException;
import io.github.shotoh.snippet.models.users.User;
import io.github.shotoh.snippet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SnippetUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Autowired
    public SnippetUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("username", "User not found with this username");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(user.getEncryptedPassword())
                .roles("USER")
                .build();
    }
}
