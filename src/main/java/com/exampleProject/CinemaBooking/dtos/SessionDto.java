package com.exampleProject.CinemaBooking.dtos;

import com.exampleProject.CinemaBooking.models.Session;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessionDto {
    private Long id;
    private String movieTitle;
    private String hallName;
    private LocalDateTime startTime;
    private Double ticketPrice;

    public SessionDto(Session session){
        id = session.getId();
        movieTitle = session.getMovie().getTitle();
        hallName = session.getHall().getName();
        startTime = session.getStartTime();
        ticketPrice = session.getTicketPrice();
    }
}
