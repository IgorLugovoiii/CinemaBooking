package com.exampleProject.CinemaBooking.dtos;

import com.exampleProject.CinemaBooking.models.Booking;
import com.exampleProject.CinemaBooking.models.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDto {
    private Long id;
    private String username; // даємо лише ім'я користув.
    private String movieTitle;
    private List<String> seats;
    private LocalDateTime bookingTime;
    private BookingStatus status;

    public BookingDto(Booking booking){
        username = booking.getUser().getUsername();
        movieTitle = booking.getSession().getMovie().getTitle();
        seats = booking.getSeats();
        bookingTime = booking.getBookingTime();
        status = booking.getStatus();
    }
}
