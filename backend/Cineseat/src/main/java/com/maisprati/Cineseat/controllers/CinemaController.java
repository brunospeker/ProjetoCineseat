package com.maisprati.Cineseat.controllers;

import com.maisprati.Cineseat.dto.CinemaDTO;
import com.maisprati.Cineseat.entities.Cinema;
import com.maisprati.Cineseat.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cinemas")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping
    public ResponseEntity<List<CinemaDTO>> findAll() {
        return ResponseEntity.ok(cinemaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CinemaDTO> findById(@PathVariable Long id) {
        Optional<CinemaDTO> cinema = cinemaService.findById(id);
        return cinema.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/city/{cityName}")
    public ResponseEntity<List<CinemaDTO>> findByCityName(@PathVariable String cityName) {
        return ResponseEntity.ok(cinemaService.findByCityName(cityName));
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<CinemaDTO>> findByState(@PathVariable String state) {
        return ResponseEntity.ok(cinemaService.findByState(state));
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<List<CinemaDTO>> findByAddress(@PathVariable String address) {
        return ResponseEntity.ok(cinemaService.findByAddress(address));
    }

    @GetMapping("/enabled")
    public ResponseEntity<List<CinemaDTO>> findByEnableTrue() {
        return ResponseEntity.ok(cinemaService.findByEnableTrue());
    }

    @GetMapping("/bomboniere")
    public ResponseEntity<List<CinemaDTO>> findByHasBomboniereTrue() {
        return ResponseEntity.ok(cinemaService.findByHasBomboniereTrue());
    }

    @GetMapping("/corporation/{corporation}")
    public ResponseEntity<List<CinemaDTO>> findByCorporation(@PathVariable String corporation) {
        return ResponseEntity.ok(cinemaService.findByCorporation(corporation));
    }

    @GetMapping("/totalRooms/{rooms}")
    public ResponseEntity<List<CinemaDTO>> findByTotalRooms(@PathVariable Integer rooms) {
        return ResponseEntity.ok(cinemaService.findByTotalRooms(rooms));
    }

    @PostMapping
    public ResponseEntity<CinemaDTO> createCinema(@RequestBody Cinema cinema) {
        return ResponseEntity.ok(cinemaService.createCinema(cinema));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CinemaDTO> updateCinema(@PathVariable Long id, @RequestBody CinemaDTO dto) {
        Optional<CinemaDTO> updatedCinema = cinemaService.updateCinema(id, dto);
        return updatedCinema.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCinema(@PathVariable Long id) {
        boolean deleted = cinemaService.deleteCinema(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
