package com.exampleProject.CinemaBooking.controllers;

import com.exampleProject.CinemaBooking.dtos.BookingDto;
import com.exampleProject.CinemaBooking.models.Booking;
import com.exampleProject.CinemaBooking.models.Session;
import com.exampleProject.CinemaBooking.models.User;
import com.exampleProject.CinemaBooking.models.enums.BookingStatus;
import com.exampleProject.CinemaBooking.security.TestSecurityConfig;
import com.exampleProject.CinemaBooking.services.BookingService;
import com.exampleProject.CinemaBooking.services.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Import(TestSecurityConfig.class)
@WebMvcTest(BookingController.class)
public class TestBookingController {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;
    @MockitoBean
    private BookingService bookingService;
    private BookingDto bookingDto;

    @BeforeEach
    public void setUp() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUser(new User());
        booking.setSession(new Session());
        booking.setSeats(List.of("A1", "A2", "A3"));
        booking.setBookingTime(LocalDateTime.of(2030, 6, 1, 12, 0));
        booking.setStatus(BookingStatus.ACTIVE);
        bookingDto = new BookingDto(booking);
    }

    @Test
    public void testFindAll() throws Exception {
        Page<BookingDto> page = new PageImpl<>(Collections.singletonList(bookingDto));
        Mockito.when(bookingService.findAll(Mockito.any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/booking")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    public void testFindById() throws Exception {
        Mockito.when(bookingService.findById(1L)).thenReturn(bookingDto);

        mockMvc.perform(get("/api/booking/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testCreateBooking() throws Exception{
        Mockito.when(bookingService.createBooking(Mockito.any(Booking.class))).thenReturn(bookingDto);

        mockMvc.perform(post("/api/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testUpdateBooking() throws Exception{
        Mockito.when(bookingService.updateBooking(Mockito.any(Booking.class),Mockito.eq(1L)))
                .thenReturn(bookingDto);

        mockMvc.perform(put("/api/booking/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookingDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testDeleteBooking() throws Exception {
        Mockito.doNothing().when(bookingService).deleteById(1L);

        mockMvc.perform(delete("/api/booking/{id}",1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFindBookingByBookingTime() throws Exception{
        Mockito.when(bookingService.findBookingByBookingTime(bookingDto.getBookingTime()))
                .thenReturn(Collections.singletonList(bookingDto));

        mockMvc.perform(get("/api/booking/by-booking-time/{time}",bookingDto.getBookingTime())
                .param("bookingTime", bookingDto.getBookingTime().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
