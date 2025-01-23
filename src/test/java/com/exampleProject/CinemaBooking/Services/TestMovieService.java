package com.exampleProject.CinemaBooking.Services;

import com.exampleProject.CinemaBooking.models.Movie;
import com.exampleProject.CinemaBooking.repositories.MovieRepository;
import com.exampleProject.CinemaBooking.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

public class TestMovieService {
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieService movieService;
    private Movie movie;

    @BeforeEach
    public void setUp() {
//        MockitoAnnotations.openMocks(this);//метод який створює та інжектує ініціалізовані моки, те ж саме що й @ExtendWith(MockitoExtension.class), просто ця анотація з junit5 а метод з junit4
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Title");
        movie.setDescription("Description.Description.Description.Description.Description.Description.");
        movie.setGenre("Genre");
        movie.setDuration(320);
        movie.setAgeRating("18+");
    }

    @Test
    public void testFindAll() {
        Mockito.when(movieRepository.findAll()).thenReturn(Collections.singletonList(movie));

        List<Movie> result = movieService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(movie.getId(), result.getFirst().getId());
        assertEquals(movie.getTitle(), result.getFirst().getTitle());
        Mockito.verify(movieRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testFindById(){
        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Optional<Movie> result = movieService.findById(1L);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(movie.getId(), result.get().getId());
        assertEquals(movie.getTitle(), result.get().getTitle());
        Mockito.verify(movieRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testCreateMovie(){
        Mockito.when(movieRepository.save(movie)).thenReturn(movie);

        Movie result = movieService.createMovie(movie);

        assertNotNull(result);
        assertEquals(movie.getId(), result.getId());
        assertEquals(movie.getTitle(), result.getTitle());
        Mockito.verify(movieRepository,Mockito.times(1)).save(movie);
    }

    @Test
    public void testUpdateMovie(){
        Movie updatedMovie = new Movie();
        updatedMovie.setId(1L);
        updatedMovie.setTitle("Updated title");
        updatedMovie.setDescription("Description.");
        updatedMovie.setGenre("Genre");
        updatedMovie.setDuration(320);
        updatedMovie.setAgeRating("18+");
        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        Mockito.when(movieRepository.save(movie)).thenReturn(movie);

        Movie result = movieService.updateMovie(updatedMovie, 1L);

        assertNotNull(result);
        assertEquals(movie.getId(), result.getId());
        assertEquals("Updated title", result.getTitle());
        assertEquals("Description.", result.getDescription());
        Mockito.verify(movieRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(movieRepository, Mockito.times(1)).save(movie);
    }

    @Test
    public void testDeleteByID(){
        Mockito.doNothing().when(movieRepository).deleteById(1L);

        movieRepository.deleteById(1L);

        Mockito.verify(movieRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testFindMoviesByGenre(){
        Mockito.when(movieRepository.findMoviesByGenre("Genre")).thenReturn(Collections.singletonList(movie));

        List<Movie> result = movieService.findMoviesByGenre("Genre");

        assertNotNull(result);
        assertEquals(1L, result.getFirst().getId());
        assertEquals("Genre", result.getFirst().getGenre());
        Mockito.verify(movieRepository, Mockito.times(1)).findMoviesByGenre("Genre");
    }
}
