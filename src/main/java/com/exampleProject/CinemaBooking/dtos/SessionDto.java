package com.exampleProject.CinemaBooking.dtos;

import com.exampleProject.CinemaBooking.models.Session;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessionDto {
    private Long id;
    private String movieTitle;
    private String hallName;
    @NotNull(message = "Start time can`t be empty")
    @Future(message = "Start time must be in future")
    private LocalDateTime startTime;
    @NotNull(message = "Price can`t be empty")
    @Min(value = 0, message = "Price can`t be less than 0")
    @Max(value = 5000, message = "Price can`t be more than 5000")
    private Double ticketPrice;

    public SessionDto(Session session){
        id = session.getId();
        movieTitle = session.getMovie().getTitle();
        hallName = session.getHall().getName();
        startTime = session.getStartTime();
        ticketPrice = session.getTicketPrice();
    }
}
