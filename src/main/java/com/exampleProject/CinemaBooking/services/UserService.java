package com.exampleProject.CinemaBooking.services;

import com.exampleProject.CinemaBooking.models.User;
import com.exampleProject.CinemaBooking.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User save(User user){
        return userRepository.save(user);
    }
    public Optional<User> findByUsername(String username){
        return userRepository.findUserByUsername(username);
    }
    @Transactional
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());
        return userRepository.save(existingUser);
    }
}
