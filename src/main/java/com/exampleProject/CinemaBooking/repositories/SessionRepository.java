package com.exampleProject.CinemaBooking.repositories;

import com.exampleProject.CinemaBooking.models.Movie;
import com.exampleProject.CinemaBooking.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
    List<Session> findSessionsByMovie(Movie movie);
    List<Session> findSessionsByStartTime(LocalDateTime startTime);
}
