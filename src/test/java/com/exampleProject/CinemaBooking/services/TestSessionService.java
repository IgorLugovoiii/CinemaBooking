package com.exampleProject.CinemaBooking.services;

import com.exampleProject.CinemaBooking.dtos.SessionDto;
import com.exampleProject.CinemaBooking.models.Hall;
import com.exampleProject.CinemaBooking.models.Movie;
import com.exampleProject.CinemaBooking.models.Session;
import com.exampleProject.CinemaBooking.repositories.MovieRepository;
import com.exampleProject.CinemaBooking.repositories.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestSessionService {
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private SessionService sessionService;

    private Movie movie;
    private Hall hall;
    private Session session;

    @BeforeEach
    public void setUp(){
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Title");

        hall = new Hall();
        hall.setId(1L);
        hall.setName("Main hall");

        session = new Session();
        session.setId(1L);
        session.setHall(hall);
        session.setMovie(movie);
        session.setTicketPrice(200.0);
        session.setStartTime(LocalDateTime.now().plusDays(1));
        session.setBookedSeats(Collections.emptyList());
    }

    @Test
    public void testFindAll(){
        Page<Session> sessionPage = new PageImpl<>(Collections.singletonList(session));
        Mockito.when(sessionRepository.findAll(Mockito.any(Pageable.class))).thenReturn(sessionPage);
        //any(Pageable.class) — це matcher, який використовується в Mockito, щоб повідомити, що метод findAll у репозиторії може прийняти будь-який об'єкт типу Pageable.
        Page<SessionDto> result = sessionService.findAll(Pageable.unpaged());//статичний метод у Spring Data, який повертає "порожній" Pageable об'єкт.

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(session.getId(), result.getContent().getFirst().getId());
        Mockito.verify(sessionRepository, Mockito.times(1)).findAll(Mockito.any(Pageable.class));
    }
    @Test
    public void testFindById(){
        Mockito.when(sessionRepository.findById(1L)).thenReturn(Optional.ofNullable(session));

        SessionDto result = sessionService.findById(1L);

        assertNotNull(movieRepository);
        assertEquals(session.getId(), result.getId());
        assertEquals(session.getHall().getName(), result.getHallName());
        Mockito.verify(sessionRepository,Mockito.times(1)).findById(1L);
    }

    @Test
    public void testCreateSession(){
        Mockito.when(sessionRepository.save(session)).thenReturn(session);

        SessionDto result = sessionService.createSession(session);

        assertNotNull(result);
        assertEquals(session.getId(), result.getId());
        assertEquals(session.getMovie().getTitle(), result.getMovieTitle());
        Mockito.verify(sessionRepository, Mockito.times(1)).save(session);
    }

    @Test
    public void testUpdateMovie(){
        Mockito.when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        Mockito.when(sessionRepository.save(session)).thenReturn(session);

        Session updatedSession = new Session();
        updatedSession.setHall(hall);
        updatedSession.setMovie(movie);
        updatedSession.setStartTime(LocalDateTime.now().plusDays(2));
        updatedSession.setTicketPrice(150.0);
        updatedSession.setBookedSeats(Collections.singletonList("A1"));

        SessionDto result = sessionService.updateSession(updatedSession, session.getId());

        assertNotNull(result);
        assertEquals(session.getId(), result.getId());
        assertEquals(150.0, result.getTicketPrice());
        Mockito.verify(sessionRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(sessionRepository, Mockito.times(1)).save(session);
    }

    @Test
    public void testDeleteById(){
        Mockito.doNothing().when(sessionRepository).deleteById(1L);

        sessionService.deleteById(1L);

        Mockito.verify(sessionRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testFindSessionsByMovie(){
        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.ofNullable(movie));
        Mockito.when(sessionRepository.findSessionsByMovie(movie)).thenReturn(Collections.singletonList(session));

        List<SessionDto> result = sessionService.findSessionsByMovie(1L);

        assertNotNull(result);
        assertEquals(movie.getTitle(), result.getFirst().getMovieTitle());
        assertEquals(session.getId(), result.getFirst().getId());
        assertEquals(1,result.size());
        Mockito.verify(movieRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(sessionRepository, Mockito.times(1)).findSessionsByMovie(movie);
    }

    @Test
    public void testFindSessionsByStartTime(){
        Mockito.when(sessionRepository.findSessionsByStartTime(Mockito.any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(session));
        List<SessionDto> result = sessionService.findSessionsByStartTime(LocalDateTime.now().plusDays(1));

        assertNotNull(result);
        assertEquals(session.getId(), result.getFirst().getId());
        assertEquals(1, result.size());
        Mockito.verify(sessionRepository, Mockito.times(1))
                .findSessionsByStartTime(Mockito.any(LocalDateTime.class));
    }
}
