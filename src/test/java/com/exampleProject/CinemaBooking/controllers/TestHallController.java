package com.exampleProject.CinemaBooking.controllers;

import com.exampleProject.CinemaBooking.models.Hall;
import com.exampleProject.CinemaBooking.security.TestSecurityConfig;
import com.exampleProject.CinemaBooking.services.CustomUserDetailsService;
import com.exampleProject.CinemaBooking.services.HallService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Import(TestSecurityConfig.class)//додаю конфігурацію, щоб пропускати етап аунтифікації
@WebMvcTest(HallController.class)//завантажую контроллер для його подальшого тестування?
public class TestHallController {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;//свторив мок, бо вимагає конфіг
    @MockitoBean
    private HallService hallService;
    private Hall hall;

    @BeforeEach
    public void setUp() {
        hall = new Hall();
        hall.setId(1L);
        hall.setName("Main hall");
        hall.setRows(10);
        hall.setSeatsPerRow(7);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Hall> halls = Collections.singletonList(hall);
        Mockito.when(hallService.findAll()).thenReturn(halls);

        mockMvc.perform(get("/api/hall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Main hall"));
    }

    @Test
    public void testFindById() throws Exception {
        Mockito.when(hallService.findById(1L)).thenReturn(Optional.ofNullable(hall));

        mockMvc.perform(get("/api/hall/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Main hall"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testCreateHall() throws Exception {
        Mockito.when(hallService.createHall(Mockito.any(Hall.class))).thenReturn(hall);

        mockMvc.perform(post("/api/hall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hall)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Main hall"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testUpdateHall() throws Exception {
        Mockito.when(hallService.updateHall(Mockito.any(Hall.class), eq(1L))).thenReturn(hall);

        mockMvc.perform(put("/api/hall/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hall)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Main hall"));
    }

    @Test
    public void testDeleteHall() throws Exception {
        Mockito.doNothing().when(hallService).deleteById(1L);

        mockMvc.perform(delete("/api/hall/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFindHallsBySeatsPerRow() throws Exception {
        List<Hall> halls = Collections.singletonList(hall);
        Mockito.when(hallService.findHallsBySeatsPerRow(10)).thenReturn(halls);

        mockMvc.perform(get("/api/hall/seats-per-row/{seatsPerRow}", 10))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Main hall"));
    }
}
