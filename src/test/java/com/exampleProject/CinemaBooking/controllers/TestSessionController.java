package com.exampleProject.CinemaBooking.controllers;

import com.exampleProject.CinemaBooking.dtos.SessionDto;
import com.exampleProject.CinemaBooking.models.Hall;
import com.exampleProject.CinemaBooking.models.Movie;
import com.exampleProject.CinemaBooking.models.Session;
import com.exampleProject.CinemaBooking.security.TestSecurityConfig;
import com.exampleProject.CinemaBooking.services.CustomUserDetailsService;
import com.exampleProject.CinemaBooking.services.SessionService;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestSecurityConfig.class)
@WebMvcTest(SessionController.class)
public class TestSessionController {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;
    @MockitoBean
    private SessionService sessionService;
    private SessionDto sessionDto;

    @BeforeEach
    public void setUp(){
        Session session = new Session();
        session.setId(1L);
        session.setHall(new Hall());
        session.setMovie(new Movie());
        session.setTicketPrice(100.0);
        session.setBookedSeats(List.of("A1", "A2", "A3"));
        session.setStartTime(LocalDateTime.of(2030, 6, 1, 12, 0));
        sessionDto = new SessionDto(session);
    }

    @Test
    public void testFindAll() throws Exception{
        Page<SessionDto> page = new PageImpl<>(Collections.singletonList(sessionDto));

        Mockito.when(sessionService.findAll(Mockito.any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/session")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    public void testFindById() throws Exception{
        Mockito.when(sessionService.findById(1L)).thenReturn(sessionDto);

        mockMvc.perform(get("/api/session/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testCreateSession()throws Exception{
        Mockito.when(sessionService.createSession(Mockito.any(Session.class))).thenReturn(sessionDto);

        mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testUpdateSession() throws Exception{
        Mockito.when(sessionService.updateSession(Mockito.any(Session.class),Mockito.eq(1L)))
                .thenReturn(sessionDto);

        mockMvc.perform(put("/api/session/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testDeleteSession() throws Exception{
        Mockito.doNothing().when(sessionService).deleteById(1L);

        mockMvc.perform(delete("/api/session/{id}",1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFindSessionByStartTime()throws Exception{
        Mockito.when(sessionService.findSessionsByStartTime(sessionDto.getStartTime()))
                .thenReturn(List.of(sessionDto));

        mockMvc.perform(get("/api/session/by-start-time")
                .param("startTime", sessionDto.getStartTime().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    public void testFindSessionByMovie()throws Exception{
        Mockito.when(sessionService.findSessionsByMovie(1L)).thenReturn(List.of(sessionDto));

        mockMvc.perform(get("/api/session/by-movie/{movieId}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
