package com.exampleProject.CinemaBooking.services;

import com.exampleProject.CinemaBooking.dtos.SessionDto;
import com.exampleProject.CinemaBooking.models.Movie;
import com.exampleProject.CinemaBooking.models.Session;
import com.exampleProject.CinemaBooking.repositories.MovieRepository;
import com.exampleProject.CinemaBooking.repositories.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository, MovieRepository movieRepository){
        this.sessionRepository = sessionRepository;
        this.movieRepository = movieRepository;
    }
    private SessionDto convertToSessionDto(Session session){
        return new SessionDto(session);
    }
    @Transactional(readOnly = true)
    public Page<SessionDto> findAll(Pageable pageable){
        Page<Session> sessionPage = sessionRepository.findAll(pageable);
        return sessionPage.map(this::convertToSessionDto);
    }
    @Transactional(readOnly = true)
    public SessionDto findById(Long id){
        return convertToSessionDto(sessionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }
    @Transactional
    public SessionDto createSession(Session session){
        return convertToSessionDto(sessionRepository.save(session));
    }
    @Transactional
    public SessionDto updateSession(Session updatedSession, Long id){
        Session oldSession = sessionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        oldSession.setHall(updatedSession.getHall());
        oldSession.setMovie(updatedSession.getMovie());
        oldSession.setBookedSeats(updatedSession.getBookedSeats());
        oldSession.setStartTime(updatedSession.getStartTime());
        oldSession.setTicketPrice(updatedSession.getTicketPrice());

        return convertToSessionDto(sessionRepository.save(oldSession));
    }
    @Transactional
    public void deleteById(Long id){
        sessionRepository.deleteById(id);
    }
    @Transactional
    public List<SessionDto> findSessionsByMovie(Long movieId){
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(EntityNotFoundException::new);
        return sessionRepository.findSessionsByMovie(movie).stream()
                .map(this::convertToSessionDto)
                .toList();
    }
    @Transactional
    public List<SessionDto> findSessionsByStartTime(LocalDateTime startTime){
        return sessionRepository.findSessionsByStartTime(startTime).stream()
                .map(this::convertToSessionDto)
                .toList();
    }
}
