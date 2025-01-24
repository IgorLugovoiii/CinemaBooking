package com.exampleProject.CinemaBooking.Services;

import com.exampleProject.CinemaBooking.dtos.BookingDto;
import com.exampleProject.CinemaBooking.models.Booking;
import com.exampleProject.CinemaBooking.models.Session;
import com.exampleProject.CinemaBooking.models.User;
import com.exampleProject.CinemaBooking.models.enums.BookingStatus;
import com.exampleProject.CinemaBooking.repositories.BookingRepository;
import com.exampleProject.CinemaBooking.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestBookingService {
    @Mock
    private BookingRepository bookingRepository;
    @InjectMocks
    private BookingService bookingService;

    private Booking booking;

    @BeforeEach
    public void setUp(){
        booking = new Booking();

        booking.setId(1L);
        booking.setUser(new User());
        booking.setSession(new Session());
        booking.setStatus(BookingStatus.ACTIVE);
        booking.setBookingTime(LocalDateTime.now().plusDays(1));
        booking.setSeats(List.of("A1","A2","A3"));
    }

    @Test
    public void testFindAll(){
        Page<Booking> bookingPage = new PageImpl<>(Collections.singletonList(booking));
        Mockito.when(bookingRepository.findAll(Mockito.any(Pageable.class))).thenReturn(bookingPage);

        Page<BookingDto> result = bookingService.findAll(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(booking.getId(), result.getContent().getFirst().getId());
        Mockito.verify(bookingRepository, Mockito.times(1))
                .findAll(Mockito.any(Pageable.class));
    }

    @Test
    public void testFindById(){
        Mockito.when(bookingRepository.findById(1L)).thenReturn(Optional.ofNullable(booking));

        BookingDto result = bookingService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(booking.getStatus(), result.getStatus());
        Mockito.verify(bookingRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testCreateBooking(){
        Mockito.when(bookingRepository.save(booking)).thenReturn(booking);

        BookingDto result = bookingService.createBooking(booking);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(List.of("A1","A2","A3"),result.getSeats());
        Mockito.verify(bookingRepository, Mockito.times(1)).save(booking);
    }

    @Test
    public void testUpdateBooking(){
        Mockito.when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        Mockito.when(bookingRepository.save(booking)).thenReturn(booking);

        Booking updatedBooking = new Booking();
        updatedBooking.setId(1L);
        updatedBooking.setUser(booking.getUser());
        updatedBooking.setSession(booking.getSession());
        updatedBooking.setSeats(List.of("A1", "A2"));
        updatedBooking.setBookingTime(LocalDateTime.now().plusDays(2));
        updatedBooking.setStatus(BookingStatus.COMPLETED);

        BookingDto result = bookingService.updateBooking(updatedBooking, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(BookingStatus.COMPLETED, result.getStatus());
        assertEquals(List.of("A1","A2"),result.getSeats());
        Mockito.verify(bookingRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(bookingRepository, Mockito.times(1)).save(booking);
    }

    @Test
    public void testDeleteById(){
        Mockito.doNothing().when(bookingRepository).deleteById(1L);

        bookingRepository.deleteById(1L);

        Mockito.verify(bookingRepository, Mockito.times(1)).deleteById(1L);
    }
    @Test
    public void testFindBookingByBookingTime(){
        Mockito.when(bookingRepository.findBookingByBookingTime(Mockito.any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(booking));

        List<BookingDto> result = bookingService.findBookingByBookingTime(LocalDateTime.now().plusDays(1));

        assertNotNull(result);
        assertEquals(booking.getId(), result.getFirst().getId());
        assertEquals(1, result.size());
        Mockito.verify(bookingRepository, Mockito.times(1))
                .findBookingByBookingTime(Mockito.any(LocalDateTime.class));
    }
}
