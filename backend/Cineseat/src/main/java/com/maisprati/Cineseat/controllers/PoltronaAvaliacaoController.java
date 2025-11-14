package com.maisprati.Cineseat.controllers;

import com.maisprati.Cineseat.dto.PoltronaAvaliacaoDTO;
import com.maisprati.Cineseat.service.PoltronaAvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/poltrona-avaliacoes")
@Tag(name = "Avaliações das Poltronas", description = "Gerencia avaliações de poltronas.")
public class PoltronaAvaliacaoController {

    @Autowired
    private PoltronaAvaliacaoService avaliacaoService;

    @GetMapping
    @Operation(summary = "Listar todas as avaliações", description = "Retorna todas as avaliações de poltronas.")
    public ResponseEntity<List<PoltronaAvaliacaoDTO>> getAllAvaliacoes() {
        return ResponseEntity.ok(avaliacaoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação por ID", description = "Retorna a avaliação de uma poltrona pelo seu ID.")
    public ResponseEntity<PoltronaAvaliacaoDTO> getAvaliacaoById(@PathVariable Long id) {
        return avaliacaoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova avaliação", description = "Cria uma nova avaliação para uma poltrona.")
    public ResponseEntity<PoltronaAvaliacaoDTO> createAvaliacao(@RequestBody PoltronaAvaliacaoDTO dto) {
        return ResponseEntity.ok(avaliacaoService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar avaliação", description = "Edita os dados de uma avaliação existente.")
    public ResponseEntity<PoltronaAvaliacaoDTO> updateAvaliacao(@PathVariable Long id, @RequestBody PoltronaAvaliacaoDTO dto) {
        return avaliacaoService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir avaliação", description = "Remove uma avaliação de poltrona.")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable Long id) {
        return avaliacaoService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/poltrona/{poltronaId}")
    @Operation(summary = "Buscar avaliações por poltrona", description = "Retorna todas as avaliações de uma poltrona específica.")
    public ResponseEntity<List<PoltronaAvaliacaoDTO>> getByPoltrona(@PathVariable Long poltronaId) {
        return ResponseEntity.ok(avaliacaoService.findByPoltrona(poltronaId));
    }

    @GetMapping("/sala/{salaId}")
    @Operation(summary = "Buscar avaliações por sala", description = "Retorna as avaliações de poltronas dentro de uma sala específica.")
    public ResponseEntity<List<PoltronaAvaliacaoDTO>> getBySala(@PathVariable Long salaId) {
        return ResponseEntity.ok(avaliacaoService.findBySala(salaId));
    }

    @GetMapping("/sala/{salaId}/comentarios")
    @Operation(summary = "Listar avaliações com comentários", description = "Retorna avaliações de poltronas em uma sala que possuem comentários.")
    public ResponseEntity<List<PoltronaAvaliacaoDTO>> getComentariosBySala(@PathVariable Long salaId) {
        List<PoltronaAvaliacaoDTO> list = avaliacaoService.findComentariosBySala(salaId);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/count/sala/{salaId}")
    @Operation(summary = "Contar avaliações por sala", description = "Retorna a quantidade total de avaliações de poltronas na sala.")
    public ResponseEntity<Long> countBySala(@PathVariable Long salaId) {
        return ResponseEntity.ok(avaliacaoService.countBySala(salaId));
    }

    @GetMapping("/poltronas/{idPoltrona}/media")
    @Operation(summary = "Média geral da poltrona", description = "Retorna a média geral calculada das avaliações de uma poltrona.")
    public ResponseEntity<Double> getMediaGeralByPoltrona(@PathVariable Long idPoltrona) {
        Optional<Double> media = avaliacaoService.getMediaGeralByPoltrona(idPoltrona);
        return media.map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(0.0));
    }
}
