package com.exampleProject.CinemaBooking.models;

import jakarta.persistence.*;
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
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "genre")
    private String genre;
    @Column(name = "duration", nullable = false)
    private Integer duration;
    @Column(name = "age_rating")
    private String ageRating;
}
