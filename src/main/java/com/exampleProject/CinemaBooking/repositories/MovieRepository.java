package com.exampleProject.CinemaBooking.repositories;

import com.exampleProject.CinemaBooking.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    List<Movie> findMoviesByGenre(String genre);
}
