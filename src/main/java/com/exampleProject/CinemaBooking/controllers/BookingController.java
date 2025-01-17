package com.exampleProject.CinemaBooking.controllers;

import com.exampleProject.CinemaBooking.dtos.BookingDto;
import com.exampleProject.CinemaBooking.models.Booking;
import com.exampleProject.CinemaBooking.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;
    @Autowired
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<Page<BookingDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return new ResponseEntity<>(bookingService.findAll(pageable), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> findById(@PathVariable Long id){
        return new ResponseEntity<>(bookingService.findById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody Booking booking){
        return new ResponseEntity<>(bookingService.createBooking(booking),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBooking(@RequestBody Booking booking, @PathVariable Long id){
        return new ResponseEntity<>(bookingService.updateBooking(booking, id),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id){
        bookingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/by-booking-time/{time}")
    public ResponseEntity<List<BookingDto>> findBookingByBookingTime(@RequestParam LocalDateTime bookingTime){
        return new ResponseEntity<>(bookingService.findBookingByBookingTime(bookingTime),HttpStatus.OK);
    }
}
