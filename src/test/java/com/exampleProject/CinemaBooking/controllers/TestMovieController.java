package com.exampleProject.CinemaBooking.controllers;

import com.exampleProject.CinemaBooking.models.Movie;
import com.exampleProject.CinemaBooking.security.TestSecurityConfig;
import com.exampleProject.CinemaBooking.services.CustomUserDetailsService;
import com.exampleProject.CinemaBooking.services.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Import(TestSecurityConfig.class)
@WebMvcTest(MovieController.class)
public class TestMovieController {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;
    @MockitoBean
    private MovieService movieService;
    private Movie movie;

    @BeforeEach
    public void setUp(){
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Title");
        movie.setGenre("Genre");
        movie.setDuration(120);
        movie.setAgeRating("18+");
    }

    @Test
    public void testFindAll() throws Exception{
        List<Movie> movies = Collections.singletonList(movie);
        Mockito.when(movieService.findAll()).thenReturn(movies);

        mockMvc.perform(get("/api/movie"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Title"));
    }

    @Test
    public void testFindById() throws Exception{
        Mockito.when(movieService.findById(1L)).thenReturn(Optional.of(movie));

        mockMvc.perform(get("/api/movie/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    public void testCreateMovie() throws Exception{
        Mockito.when(movieService.createMovie(Mockito.any(Movie.class))).thenReturn(movie);

        mockMvc.perform(post("/api/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    public void testUpdateMovie() throws Exception{
        Mockito.when(movieService.updateMovie(Mockito.any(Movie.class),eq(1L))).thenReturn(movie);

        mockMvc.perform(put("/api/movie/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    public void testDeleteMovie() throws Exception{
        Mockito.doNothing().when(movieService).deleteById(1L);

        mockMvc.perform(delete("/api/movie/{id}",1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFindMoviesByGenre() throws Exception{
        List<Movie> movies = Collections.singletonList(movie);
        Mockito.when(movieService.findMoviesByGenre("Genre")).thenReturn(movies);

        mockMvc.perform(get("/api/movie/movies-by-genre/{genre}","Genre"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Title"));

    }
}
