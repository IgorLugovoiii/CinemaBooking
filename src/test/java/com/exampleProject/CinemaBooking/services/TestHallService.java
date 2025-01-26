package com.exampleProject.CinemaBooking.services;

import com.exampleProject.CinemaBooking.models.Hall;
import com.exampleProject.CinemaBooking.repositories.HallRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestHallService {
    @Mock
    private HallRepository hallRepository;
    @InjectMocks
    private HallService hallService;
    private Hall hall;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        hall = new Hall();
        hall.setId(1L);
        hall.setName("Main hall");
        hall.setRows(10);
        hall.setSeatsPerRow(5);
    }

    @Test
    public void testFindAll(){
        Mockito.when(hallRepository.findAll()).thenReturn(Collections.singletonList(hall));

        List<Hall> halls = hallService.findAll();

        assertNotNull(halls);
        assertFalse(halls.isEmpty());
        assertEquals(hall.getId(), halls.getFirst().getId());
        assertEquals(1,halls.size());
        assertEquals(hall.getName(), halls.getFirst().getName());
        Mockito.verify(hallRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testFindById_HallExists(){
        Mockito.when(hallRepository.findById(1L)).thenReturn(Optional.ofNullable(hall));

        Optional<Hall> result = hallService.findById(1L);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals(hall.getName(), result.get().getName());
        Mockito.verify(hallRepository,Mockito.times(1)).findById(1L);
    }

    @Test
    public void testCreateHall(){
        Mockito.when(hallRepository.save(hall)).thenReturn(hall);

        Hall createdHall = hallService.createHall(hall);

        assertNotNull(createdHall);
        assertEquals(1L, createdHall.getId());
        assertEquals(hall.getName(), createdHall.getName());
        Mockito.verify(hallRepository, Mockito.times(1)).save(hall);
    }

    @Test
    public void testUpdateHall(){
        Hall updatedHall = new Hall();
        updatedHall.setName("Updated Hall");
        updatedHall.setRows(12);
        updatedHall.setSeatsPerRow(20);
        Mockito.when(hallRepository.findById(1L)).thenReturn(Optional.of(hall));
        Mockito.when(hallRepository.save(hall)).thenReturn(hall);

        Hall result = hallService.updateHall(updatedHall, 1L);

        assertNotNull(result);
        assertEquals("Updated Hall", result.getName());
        Mockito.verify(hallRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(hallRepository, Mockito.times(1)).save(hall);
    }

    @Test
    public void testDeleteHall(){
        Mockito.doNothing().when(hallRepository).deleteById(1L);

        hallRepository.deleteById(1L);

        Mockito.verify(hallRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testFindHallsBySeatsPerRow(){
        Mockito.when(hallRepository.findHallsBySeatsPerRow(5)).thenReturn(Collections.singletonList(hall));

        List<Hall> result = hallService.findHallsBySeatsPerRow(5);

        assertNotNull(result);
        assertEquals(hall.getSeatsPerRow(), result.getFirst().getSeatsPerRow());
        assertEquals(1, result.size());
        Mockito.verify(hallRepository, Mockito.times(1)).findHallsBySeatsPerRow(5);
    }
}
