package com.exampleProject.CinemaBooking.controllers;

import com.exampleProject.CinemaBooking.models.Movie;
import com.exampleProject.CinemaBooking.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> findAll(){
        return new ResponseEntity<>(movieService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Movie>> findById(@PathVariable Long id){
        return new ResponseEntity<>(movieService.findById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        return new ResponseEntity<>(movieService.createMovie(movie),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie, @PathVariable Long id){
        return new ResponseEntity<>(movieService.updateMovie(movie, id), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        movieService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/movies-by-genre/{genre}")
    public ResponseEntity<List<Movie>> findMoviesByGenre(@PathVariable String genre){
        return new ResponseEntity<>(movieService.findMoviesByGenre(genre),HttpStatus.OK);
    }

}
