package com.exampleProject.CinemaBooking.services;

import com.exampleProject.CinemaBooking.dtos.BookingDto;
import com.exampleProject.CinemaBooking.models.Booking;
import com.exampleProject.CinemaBooking.repositories.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    @Autowired
    public BookingService(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }
    private BookingDto convertToBookingDto(Booking booking){
        return new BookingDto(booking);
    }
    @Transactional(readOnly = true)
    public Page<BookingDto> findAll(Pageable pageable){
        Page<Booking> bookingPage = bookingRepository.findAll(pageable);
        return bookingPage.map(this::convertToBookingDto);
    }
    @Transactional(readOnly = true)
    public BookingDto findById(Long id){
        return convertToBookingDto(bookingRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }
    @Transactional
    public BookingDto createBooking(Booking booking){
        return convertToBookingDto(bookingRepository.save(booking));
    }
    @Transactional
    public BookingDto updateBooking(Booking updatedBooking, Long id){
        Booking oldBooking = bookingRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        oldBooking.setUser(updatedBooking.getUser());
        oldBooking.setBookingTime(updatedBooking.getBookingTime());
        oldBooking.setStatus(updatedBooking.getStatus());
        oldBooking.setSession(updatedBooking.getSession());
        oldBooking.setSeats(updatedBooking.getSeats());

        return convertToBookingDto(bookingRepository.save(oldBooking));
    }
    @Transactional
    public void deleteById(Long id){
        bookingRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<BookingDto> findBookingByBookingTime(LocalDateTime bookingTime){
        return bookingRepository.findBookingByBookingTime(bookingTime).stream()
                .map(this::convertToBookingDto)
                .toList();
    }
}
