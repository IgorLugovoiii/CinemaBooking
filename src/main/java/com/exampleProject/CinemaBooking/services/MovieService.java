package com.exampleProject.CinemaBooking.services;

import com.exampleProject.CinemaBooking.models.Movie;
import com.exampleProject.CinemaBooking.repositories.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    @Autowired
    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }
    @Transactional(readOnly = true)
    public List<Movie> findAll(){
        return movieRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Optional<Movie> findById(Long id){
        return movieRepository.findById(id);
    }
    @Transactional
    public Movie createMovie(Movie movie){
        return movieRepository.save(movie);
    }
    @Transactional
    public Movie updateMovie(Movie updatedMovie, Long id){
        Movie oldMovie = movieRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        oldMovie.setTitle(updatedMovie.getTitle());
        oldMovie.setDescription(updatedMovie.getDescription());
        oldMovie.setGenre(updatedMovie.getGenre());
        oldMovie.setDuration(updatedMovie.getDuration());
        oldMovie.setAgeRating(updatedMovie.getAgeRating());

        return movieRepository.save(oldMovie);
    }
    @Transactional
    public void deleteById(Long id){
        movieRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    List<Movie> findMoviesByGenre(String genre){
        return movieRepository.findMoviesByGenre(genre);
    }
}
