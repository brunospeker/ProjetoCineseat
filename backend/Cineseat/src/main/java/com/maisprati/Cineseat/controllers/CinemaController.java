package com.maisprati.Cineseat.controllers;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maisprati.Cineseat.dto.CinemaDTO;
import com.maisprati.Cineseat.entities.Cinema;
import com.maisprati.Cineseat.service.CinemaService;

@RestController
@RequestMapping("/api/cinemas")
@Tag(name = "Cinemas", description = "Operações relacionadas aos cinemas cadastrados")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping
    @Operation(summary = "Listar todos os cinemas", description = "Retorna uma lista com todos os cinemas cadastrados.")
    public ResponseEntity<List<CinemaDTO>> findAll() {
        return ResponseEntity.ok(cinemaService.findAll());
    }

    @GetMapping("/{idCinema}")
    @Operation(summary = "Buscar cinema por ID", description = "Retorna os detalhes de um cinema específico.")
    public ResponseEntity<CinemaDTO> findById(@PathVariable Long idCinema) {
        Optional<CinemaDTO> cinema = cinemaService.findById(idCinema);
        return cinema.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cidade/{cidade}")
    @Operation(summary = "Buscar cinemas por cidade", description = "Retorna todos os cinemas localizados na cidade informada.")
    public ResponseEntity<List<CinemaDTO>> findByCidade(@PathVariable String cidade) {
        return ResponseEntity.ok(cinemaService.findByCidade(cidade));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar cinemas por estado", description = "Retorna todos os cinemas localizados no estado informado.")
    public ResponseEntity<List<CinemaDTO>> findByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(cinemaService.findByEstado(estado));
    }

    @GetMapping("/bairro/{bairro}")
    @Operation(summary = "Buscar cinemas por bairro", description = "Retorna todos os cinemas localizados no bairro informado.")
    public ResponseEntity<List<CinemaDTO>> findByBairro(@PathVariable String bairro) {
        return ResponseEntity.ok(cinemaService.findByBairro(bairro));
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar cinemas ativos", description = "Retorna todos os cinemas que estão marcados como ativos.")
    public ResponseEntity<List<CinemaDTO>> findAtivos() {
        return ResponseEntity.ok(cinemaService.findByAtivoTrue());
    }

    @GetMapping("/bomboniere")
    @Operation(summary = "Listar cinemas com bomboniere", description = "Retorna todos os cinemas que possuem bomboniere.")
    public ResponseEntity<List<CinemaDTO>> findComBomboniere() {
        return ResponseEntity.ok(cinemaService.findByTemBomboniereTrue());
    }

    @GetMapping("/salas/{totalSalas}")
    @Operation(summary = "Buscar cinemas por número de salas", description = "Retorna todos os cinemas que possuem o número exato de salas informado.")
    public ResponseEntity<List<CinemaDTO>> findByTotalSalas(@PathVariable Integer totalSalas) {
        return ResponseEntity.ok(cinemaService.findByTotalSalas(totalSalas));
    }

    @GetMapping("/intervalo/salas")
    @Operation(summary = "Buscar cinemas por intervalo de salas", description = "Retorna todos os cinemas com quantidade de salas entre os valores mínimo e máximo informados.")
    public ResponseEntity<List<CinemaDTO>> findByTotalSalasBetween(
            @RequestParam(name = "min", required = false, defaultValue = "0") Integer min,
            @RequestParam(name = "max", required = false, defaultValue = "20") Integer max) {
        if (min > max) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok(cinemaService.findByTotalSalasBetween(min, max));
    }

    @GetMapping("/nota/{mediaGeral}")
    @Operation(summary = "Buscar cinemas por média geral", description = "Retorna todos os cinemas que possuem a nota exata.")
    public ResponseEntity<List<CinemaDTO>> findByMediaGeral(@PathVariable Double mediaGeral) {
        return ResponseEntity.ok(cinemaService.findByMediaGeral(mediaGeral));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo cinema", description = "Cria um novo registro de cinema no sistema.")
    public ResponseEntity<CinemaDTO> createCinema(@RequestBody Cinema cinema) {
        return ResponseEntity.ok(cinemaService.createCinema(cinema));
    }

    @PutMapping("/{idCinema}")
    @Operation(summary = "Atualizar cinema existente", description = "Atualiza as informações de um cinema com base no ID fornecido.")
    public ResponseEntity<CinemaDTO> updateCinema(@PathVariable Long idCinema, @RequestBody CinemaDTO dto) {
        Optional<CinemaDTO> updatedCinema = cinemaService.updateCinema(idCinema, dto);
        return updatedCinema.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idCinema}")
    @Operation(summary = "Excluir cinema", description = "Remove um cinema do sistema pelo ID informado.")
    public ResponseEntity<Void> deleteCinema(@PathVariable Long idCinema) {
        boolean deleted = cinemaService.deleteCinema(idCinema);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/ranking")
    @Operation(summary = "Ranking geral dos cinemas", description = "Retorna os cinemas ordenados pela maior média geral de avaliação.")
    public ResponseEntity<List<CinemaDTO>> rankingGeral() {
        return ResponseEntity.ok(cinemaService.rankingGeral());
    }

    @GetMapping("/ranking/cidade/{cidade}")
    @Operation(summary = "Ranking de cinemas por cidade", description = "Retorna os cinemas de uma cidade, ordenados pela média geral.")
    public ResponseEntity<List<CinemaDTO>> rankingPorCidade(@PathVariable String cidade) {
        return ResponseEntity.ok(cinemaService.rankingPorCidade(cidade));
    }

    @GetMapping("/ranking/estado/{estado}")
    @Operation(summary = "Ranking de cinemas por estado", description = "Retorna os cinemas de um estado, ordenados pela média geral.")
    public ResponseEntity<List<CinemaDTO>> rankingPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(cinemaService.rankingPorEstado(estado));
    }
}
