package com.exampleProject.CinemaBooking.controllers;

import com.exampleProject.CinemaBooking.models.User;
import com.exampleProject.CinemaBooking.security.JwtUtil;
import com.exampleProject.CinemaBooking.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    @Autowired
    public AuthController(PasswordEncoder passwordEncoder, UserService userService, JwtUtil jwtUtil){
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody @Valid User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid User user){
        User existingUser = userService.findByUsername(user.getUsername()).orElseThrow(EntityNotFoundException::new);
        if(existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())){
            String token = jwtUtil.generateToken(existingUser.getUsername(), existingUser.getRole().toString());
            return ResponseEntity.ok(token);
        }
        return new ResponseEntity<>("Invalid username or password", HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody @Valid User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }
}
