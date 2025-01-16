package com.exampleProject.CinemaBooking.repositories;

import com.exampleProject.CinemaBooking.models.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingByBookingTime(LocalDateTime bookingTime);
    Page<Booking> findAll(Pageable pageable);
}
