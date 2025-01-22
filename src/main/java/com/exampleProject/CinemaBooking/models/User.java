package com.exampleProject.CinemaBooking.models;

import com.exampleProject.CinemaBooking.models.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 0, max = 30, message = "Incorrect username, length must be from 0 to 30")
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Size(min = 0, max = 100, message = "Incorrect password, length must be from 0 to 100")
    @Column(name = "password", nullable = false)
    private String password;
    @Email(message = "Invalid email format")
    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}
