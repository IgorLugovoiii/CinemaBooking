package com.exampleProject.CinemaBooking.controllers;

import com.exampleProject.CinemaBooking.dtos.SessionDto;
import com.exampleProject.CinemaBooking.models.Movie;
import com.exampleProject.CinemaBooking.models.Session;
import com.exampleProject.CinemaBooking.services.SessionService;
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
@RequestMapping("/api/session")
public class SessionController {
    private final SessionService sessionService;
    @Autowired
    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }
    @GetMapping
    public ResponseEntity<Page<SessionDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return new ResponseEntity<>(sessionService.findAll(pageable),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> findById(@PathVariable Long id){
        return new ResponseEntity<>(sessionService.findById(id), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createSession(@Valid @RequestBody Session session){
        return new ResponseEntity<>(sessionService.createSession(session),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSession(@Valid @RequestBody Session session, @PathVariable Long id){
        return new ResponseEntity<>(sessionService.updateSession(session,id),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        sessionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/by-movie/{movieId}")
    public ResponseEntity<List<SessionDto>> findSessionsByMovie(@PathVariable Long movieId){
        return new ResponseEntity<>(sessionService.findSessionsByMovie(movieId),HttpStatus.OK);
    }
    @GetMapping("/by-start-time")
    public ResponseEntity<List<SessionDto>> findSessionsByStartTime(@RequestParam LocalDateTime startTime){
        return new ResponseEntity<>(sessionService.findSessionsByStartTime(startTime),HttpStatus.OK);
    }
}
