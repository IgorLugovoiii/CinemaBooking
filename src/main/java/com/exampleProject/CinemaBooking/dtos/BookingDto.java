package com.exampleProject.CinemaBooking.dtos;

import com.exampleProject.CinemaBooking.models.Booking;
import com.exampleProject.CinemaBooking.models.Movie;
import com.exampleProject.CinemaBooking.models.Session;
import com.exampleProject.CinemaBooking.models.enums.BookingStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
public class BookingDto {
    private Long id;
    private String username; // даємо лише ім'я користув.
    private String movieTitle;
    @Size(min = 0, max = 200, message = "Wrong number of seats")
    private List<String> seats;
    @Future(message = "Booking time must be in future")
    private LocalDateTime bookingTime;
    @NotBlank(message = "Status can`t be empty")
    private BookingStatus status;

    public BookingDto(Booking booking){
        username = booking.getUser().getUsername();
        movieTitle = Optional.ofNullable(booking.getSession())
                .map(Session::getMovie)
                .map(Movie::getTitle)
                .orElse("No movie assigned");
        seats = booking.getSeats();
        bookingTime = booking.getBookingTime();
        status = booking.getStatus();
    }
}
