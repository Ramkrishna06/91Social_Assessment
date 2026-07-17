package com.example.Assignment_91Social.Controller;

import com.example.Assignment_91Social.Models.User;
import com.example.Assignment_91Social.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam User.Role role) {

        User savedUser = userService.registerUser(name, email, role);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email) {
        User user = userService.login(email);
        return ResponseEntity.ok(user);
    }
}