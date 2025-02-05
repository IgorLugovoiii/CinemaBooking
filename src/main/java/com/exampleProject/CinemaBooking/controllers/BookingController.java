package com.exampleProject.CinemaBooking.controllers;

import com.exampleProject.CinemaBooking.dtos.BookingDto;
import com.exampleProject.CinemaBooking.models.Booking;
import com.exampleProject.CinemaBooking.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Booking controller", description = "Manages booking")//Додається на рівні класу, щоб методи автоматично групувалися
public class BookingController {
    private final BookingService bookingService;
    @Autowired
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }
    @Operation(summary = "Get all bookings", description = "Returns a list of bookings")//Описує конкретний API-метод у контролері.
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
    public ResponseEntity<?> createBooking(@Valid @RequestBody Booking booking){
        return new ResponseEntity<>(bookingService.createBooking(booking),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@Valid @RequestBody Booking booking, @PathVariable Long id){
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
