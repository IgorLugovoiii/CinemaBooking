package com.exampleProject.CinemaBooking.models;

import com.exampleProject.CinemaBooking.models.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "booking")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;
    @ElementCollection//анотація, яка створює додаткову таблицю для збереження даних про колекцію
    private List<String> seats;
    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
