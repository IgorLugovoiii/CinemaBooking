package com.exampleProject.CinemaBooking.services;

import com.exampleProject.CinemaBooking.models.User;
import com.exampleProject.CinemaBooking.models.enums.Role;
import com.exampleProject.CinemaBooking.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestUserService {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;
    private User user;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setId(1L);
        user.setUsername("Username");
        user.setPassword("password");
        user.setRole(Role.USER);
        user.setEmail("qeadzc4065@gmail.com");
    }
    @Test
    public void testSaveUser(){
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Username", result.getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void testFindByUsername(){
        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername(user.getUsername());

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals(user.getUsername(), result.get().getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).findUserByUsername(user.getUsername());
    }

    @Test
    public void testUpdateUser(){
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("Username1");
        updatedUser.setPassword("password1");
        updatedUser.setRole(Role.ADMIN);
        updatedUser.setEmail("qeadzc4065@gmail.com");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.encode(updatedUser.getPassword())).thenReturn(updatedUser.getPassword());
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(updatedUser.getRole(), result.getRole());
        assertEquals(updatedUser.getUsername(), result.getUsername());
        assertEquals(updatedUser.getPassword(), result.getPassword());
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(updatedUser.getPassword());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }
}
