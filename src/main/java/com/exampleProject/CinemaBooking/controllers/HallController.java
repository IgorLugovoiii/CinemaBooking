package com.exampleProject.CinemaBooking.controllers;

import com.exampleProject.CinemaBooking.models.Hall;
import com.exampleProject.CinemaBooking.services.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hall")
public class HallController {
    private final HallService hallService;
    @Autowired
    public HallController(HallService hallService){
        this.hallService = hallService;
    }
    @GetMapping
    public ResponseEntity<List<Hall>> findAll(){
        return new ResponseEntity<>(hallService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Hall>> findById(@PathVariable Long id){
        return new ResponseEntity<>(hallService.findById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Hall> createHall(@RequestBody Hall hall){
        return new ResponseEntity<>(hallService.createHall(hall),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Hall> updateHall(@RequestBody Hall hall, @PathVariable Long id){
        return new ResponseEntity<>(hallService.updateHall(hall, id), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        hallService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/seats-per-row/{seatsPerRow}")
    public ResponseEntity<List<Hall>> findHallsBySeatsPerRow(@PathVariable Integer seatsPerRow){
        return new ResponseEntity<>(hallService.findHallsBySeatsPerRow(seatsPerRow),HttpStatus.OK);
    }
}
