package com.maisprati.Cineseat.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maisprati.Cineseat.dto.CinemaAvaliacaoDTO;
import com.maisprati.Cineseat.service.CinemaAvaliacaoService;

@RestController
@RequestMapping("/api/cinema-avaliacoes")
@Tag(name = "Avaliações dos Cinemas", description = "Gerencia as avaliações dos cinemas e atualiza as médias gerais.")
public class CinemaAvaliacaoController {

    @Autowired
    private CinemaAvaliacaoService avaliacaoService;

    @GetMapping
    @Operation(summary = "Listar todas as avaliações", description = "Retorna todas as avaliações registradas no sistema.")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAllAvaliacoes() {
        return ResponseEntity.ok(avaliacaoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação por ID", description = "Retorna os detalhes de uma avaliação específica.")
    public ResponseEntity<CinemaAvaliacaoDTO> getAvaliacaoById(@PathVariable Long id) {
        return avaliacaoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova avaliação", description = "Cria uma nova avaliação para um cinema e atualiza a média geral.")
    public ResponseEntity<CinemaAvaliacaoDTO> createAvaliacao(@RequestBody CinemaAvaliacaoDTO dto) {
        return ResponseEntity.ok(avaliacaoService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar avaliação existente", description = "Edita as notas e o comentário de uma avaliação já existente.")
    public ResponseEntity<CinemaAvaliacaoDTO> updateAvaliacao(@PathVariable Long id, @RequestBody CinemaAvaliacaoDTO dto) {
        return avaliacaoService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma avaliação", description = "Remove uma avaliação e atualiza a média geral do cinema.")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable Long id) {
        return avaliacaoService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/cinema/{idCinema}")
    @Operation(summary = "Listar avaliações de um cinema", description = "Retorna todas as avaliações associadas a um cinema específico.")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesByCinema(@PathVariable Long idCinema) {
        return ResponseEntity.ok(avaliacaoService.findByCinema(idCinema));
    }

    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Listar avaliações de um usuário", description = "Retorna todas as avaliações feitas por um usuário específico.")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesByUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(avaliacaoService.findByUsuario(idUsuario));
    }

    @GetMapping("/usuario/{idUsuario}/cinema/{idCinema}")
    @Operation(summary = "Buscar avaliações de um usuário em um cinema", description = "Retorna todas as avaliações feitas por um usuário em um cinema específico.")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesByUsuarioAndCinema(@PathVariable Long idUsuario, @PathVariable Long idCinema) {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findByUsuarioAndCinema(idUsuario, idCinema);
        return avaliacoes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/cinema/{idCinema}/comentarios")
    @Operation(summary = "Listar avaliações com comentários", description = "Retorna apenas as avaliações de um cinema que possuem comentários preenchidos.")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesComComentario(@PathVariable Long idCinema) {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findByCinemaWithComentario(idCinema);
        return avaliacoes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/count/cinema/{idCinema}")
    @Operation(summary = "Contar avaliações por cinema", description = "Retorna a quantidade total de avaliações registradas para um cinema.")
    public ResponseEntity<Long> countByCinema(@PathVariable Long idCinema) {
        return ResponseEntity.ok(avaliacaoService.countByCinema(idCinema));
    }

    @GetMapping("/count/usuario/{idUsuario}")
    @Operation(summary = "Contar avaliações por usuário", description = "Retorna a quantidade total de avaliações feitas por um usuário.")
    public ResponseEntity<Long> countByUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(avaliacaoService.countByUsuario(idUsuario));
    }

    @GetMapping("/media/cinema/{idCinema}")
    @Operation(summary = "Obter média geral de um cinema", description = "Retorna a média geral das avaliações de um cinema.")
    public ResponseEntity<Double> getMediaGeralByCinema(@PathVariable Long idCinema) {
        Double media = avaliacaoService.getMediaGeralByCinemaId(idCinema).orElse(0.0);
        return ResponseEntity.ok(media);
    }
}
