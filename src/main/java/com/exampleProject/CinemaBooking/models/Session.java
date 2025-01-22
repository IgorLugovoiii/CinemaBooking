package com.exampleProject.CinemaBooking.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    @Column(name = "ticket_price",nullable = false)
    private Double ticketPrice;
    @ElementCollection
    private List<String> bookedSeats;
}
