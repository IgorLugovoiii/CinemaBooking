package com.exampleProject.CinemaBooking.repositories;

import com.exampleProject.CinemaBooking.models.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
    List<Hall> findHallsBySeatsPerRow(Integer seatsPerRow);
}
