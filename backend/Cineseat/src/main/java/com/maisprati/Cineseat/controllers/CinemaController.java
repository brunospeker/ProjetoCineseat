package com.maisprati.Cineseat.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maisprati.Cineseat.dto.CinemaDTO;
import com.maisprati.Cineseat.entities.Cinema;
import com.maisprati.Cineseat.service.CinemaService;

@RestController
@RequestMapping("/api/cinemas")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping
    public ResponseEntity<List<CinemaDTO>> findAll() {
        return ResponseEntity.ok(cinemaService.findAll());
    }

    @GetMapping("/{idCinema}")
    public ResponseEntity<CinemaDTO> findById(@PathVariable Long idCinema) {
        Optional<CinemaDTO> cinema = cinemaService.findById(idCinema);
        return cinema.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cidade/{cidade}")
    public ResponseEntity<List<CinemaDTO>> findByCidade(@PathVariable String cidade) {
        return ResponseEntity.ok(cinemaService.findByCidade(cidade));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CinemaDTO>> findByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(cinemaService.findByEstado(estado));
    }

    @GetMapping("/bairro/{bairro}")
    public ResponseEntity<List<CinemaDTO>> findByBairro(@PathVariable String bairro) {
        return ResponseEntity.ok(cinemaService.findByBairro(bairro));
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<CinemaDTO>> findAtivos() {
        return ResponseEntity.ok(cinemaService.findByAtivoTrue());
    }

    @GetMapping("/bomboniere")
    public ResponseEntity<List<CinemaDTO>> findComBomboniere() {
        return ResponseEntity.ok(cinemaService.findByTemBomboniereTrue());
    }

    @GetMapping("/salas/{totalSalas}")
    public ResponseEntity<List<CinemaDTO>> findByTotalSalas(@PathVariable Integer totalSalas) {
        return ResponseEntity.ok(cinemaService.findByTotalSalas(totalSalas));
    }

    @GetMapping("/intervalo/salas")
public ResponseEntity<List<CinemaDTO>> findByTotalSalasBetween(
        @RequestParam(name = "min", required = false, defaultValue = "0") Integer min,
        @RequestParam(name = "max", required = false, defaultValue = "20") Integer max) {

    if (min > max) {
        return ResponseEntity.badRequest().body(null);
    }

    List<CinemaDTO> cinemas = cinemaService.findByTotalSalasBetween(min, max);
    return ResponseEntity.ok(cinemas);
}

    @PostMapping
    public ResponseEntity<CinemaDTO> createCinema(@RequestBody Cinema cinema) {
        return ResponseEntity.ok(cinemaService.createCinema(cinema));
    }

    @PutMapping("/{idCinema}")
    public ResponseEntity<CinemaDTO> updateCinema(@PathVariable Long idCinema,
            @RequestBody CinemaDTO dto) {
        Optional<CinemaDTO> updatedCinema = cinemaService.updateCinema(idCinema, dto);
        return updatedCinema.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idCinema}")
    public ResponseEntity<Void> deleteCinema(@PathVariable Long idCinema) {
        boolean deleted = cinemaService.deleteCinema(idCinema);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
