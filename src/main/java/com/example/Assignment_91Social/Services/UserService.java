package com.example.Assignment_91Social.Services;

import com.example.Assignment_91Social.Models.User;
import com.example.Assignment_91Social.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String name, String email, User.Role role) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered: " + email);
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);

        return userRepository.save(user);
    }

    public User login(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No user found with email: " + email));
    }

    public boolean isAdmin(User user) {
        return user.getRole() == User.Role.ADMIN;
    }
}