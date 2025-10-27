package com.maisprati.Cineseat.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maisprati.Cineseat.dto.CinemaAvaliacaoDTO;
import com.maisprati.Cineseat.service.CinemaAvaliacaoService;

@RestController
@RequestMapping("/api/cinema-avaliacoes")
public class CinemaAvaliacaoController {

    @Autowired
    private CinemaAvaliacaoService avaliacaoService;

    @GetMapping
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAllAvaliacoes() {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findAll();
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CinemaAvaliacaoDTO> getAvaliacaoById(@PathVariable Long id) {
        Optional<CinemaAvaliacaoDTO> avaliacao = avaliacaoService.findById(id);
        return avaliacao.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cinema/{idCinema}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesByCinema(@PathVariable Long idCinema) {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findByCinema(idCinema);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesByUsuario(@PathVariable Long idUsuario) {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findByUsuario(idUsuario);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/usuario/{idUsuario}/cinema/{idCinema}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesByUsuarioAndCinema(
            @PathVariable Long idUsuario,
            @PathVariable Long idCinema) {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findByUsuarioAndCinema(idUsuario, idCinema);
        return avaliacoes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/cinema/{idCinema}/comentarios")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesComComentario(@PathVariable Long idCinema) {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findByCinemaWithComentario(idCinema);
        return avaliacoes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/notaGeral/{nota}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getByNotaGeral(@PathVariable Integer nota) {
        return ResponseEntity.ok(avaliacaoService.findByNotaGeral(nota));
    }

    @GetMapping("/notaLimpeza/{nota}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getByNotaLimpeza(@PathVariable Integer nota) {
        return ResponseEntity.ok(avaliacaoService.findByNotaLimpeza(nota));
    }

    @GetMapping("/notaPreco/{nota}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getByNotaPreco(@PathVariable Integer nota) {
        return ResponseEntity.ok(avaliacaoService.findByNotaPreco(nota));
    }

    @GetMapping("/notaAlimentacao/{nota}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getByNotaAlimentacao(@PathVariable Integer nota) {
        return ResponseEntity.ok(avaliacaoService.findByNotaAlimentacao(nota));
    }

    @GetMapping("/notaAtendimento/{nota}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getByNotaAtendimento(@PathVariable Integer nota) {
        return ResponseEntity.ok(avaliacaoService.findByNotaAtendimento(nota));
    }

    @GetMapping("/count/cinema/{idCinema}")
    public ResponseEntity<Long> countByCinema(@PathVariable Long idCinema) {
        return ResponseEntity.ok(avaliacaoService.countByCinema(idCinema));
    }

    @GetMapping("/count/usuario/{idUsuario}")
    public ResponseEntity<Long> countByUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(avaliacaoService.countByUsuario(idUsuario));
    }

    @GetMapping("/recentes/{idCinema}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesRecentes(@PathVariable Long idCinema) {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findAvaliacoesRecentes(idCinema);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/medias/{idCinema}")
    public ResponseEntity<Optional<CinemaAvaliacaoDTO>> getAverageScore(@PathVariable Long idCinema) {
        Optional<CinemaAvaliacaoDTO> medias = avaliacaoService.findAverageRatingsByCinemaId(idCinema);
        return ResponseEntity.ok(medias);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getRankingByHighestNotes() {
        List<CinemaAvaliacaoDTO> ranking = avaliacaoService.rankingByAverageScore();
        return ResponseEntity.ok(ranking);
    }

    @GetMapping("/ranking/cidade/{cidade}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getRankingByCidade(@PathVariable String cidade) {
        List<CinemaAvaliacaoDTO> ranking = avaliacaoService.rankingByCity(cidade);
        return ResponseEntity.ok(ranking);
    }

    @GetMapping("/ranking/estado/{estado}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getRankingByEstado(@PathVariable String estado) {
        List<CinemaAvaliacaoDTO> ranking = avaliacaoService.rankingByState(estado);
        return ResponseEntity.ok(ranking);
    }

    @PostMapping
    public ResponseEntity<CinemaAvaliacaoDTO> createAvaliacao(@RequestBody CinemaAvaliacaoDTO dto) {
        CinemaAvaliacaoDTO created = avaliacaoService.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CinemaAvaliacaoDTO> updateAvaliacao(
            @PathVariable Long id,
            @RequestBody CinemaAvaliacaoDTO dto) {
        Optional<CinemaAvaliacaoDTO> updated = avaliacaoService.update(id, dto);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable Long id) {
        boolean deleted = avaliacaoService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
