package com.exampleProject.CinemaBooking.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "halls")
public class Hall {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "rows", nullable = false)
    private Integer rows;
    @Column(name = "seats_per_row", nullable = false)
    private Integer seatsPerRow;
}
