package com.exampleProject.CinemaBooking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "movies")
public class Movie {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title", nullable = false)
    @NotBlank(message = "Title can`t be empty")
    @Size(max = 100, message = "Title is to long")
    private String title;
    @Column(name = "description")
    @Size(max = 250, message = "Description can`t be longer than 250 symbols")
    private String description;
    @Column(name = "genre")
    private String genre;
    @Column(name = "duration", nullable = false)
    @NotNull(message = "Duration can`t be empty")
    @Min(value = 0, message = "Duration can`t be less than 0 minutes")
    @Max(value = 1440, message = "Duration can`t be less than 1440 minutes")
    private Integer duration;
    @Column(name = "age_rating")
    @Size(min = 0, max = 130, message = "Age must be from 0 to 130 years")
    @NotBlank(message = "Age can`t be empty")
    private String ageRating;
}
