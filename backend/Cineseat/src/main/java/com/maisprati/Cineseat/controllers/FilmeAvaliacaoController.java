package com.maisprati.Cineseat.controllers;

import com.maisprati.Cineseat.dto.FilmeAvaliacaoDTO;
import com.maisprati.Cineseat.service.FilmeAvaliacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/filme-avaliacoes")
@Tag(name = "Avaliações de Filmes", description = "Operações relacionadas às avaliações feitas em filmes")
public class FilmeAvaliacaoController {

    @Autowired
    private FilmeAvaliacaoService filmeAvaliacaoService;

    @GetMapping
    @Operation(summary = "Listar todas as avaliações", description = "Retorna todas as avaliações cadastradas no sistema.")
    public ResponseEntity<List<FilmeAvaliacaoDTO>> buscarTodasAvaliacoes() {
        return ResponseEntity.ok(filmeAvaliacaoService.buscarTodasAvaliacoes());
    }

    @GetMapping("/filme/{filmeId}")
    @Operation(summary = "Listar avaliações por filme", description = "Retorna todas as avaliações feitas para um filme específico.")
    public ResponseEntity<List<FilmeAvaliacaoDTO>> buscarAvaliacoesPorFilme(@PathVariable Long filmeId) {
        return ResponseEntity.ok(filmeAvaliacaoService.buscarAvaliacoesPorFilme(filmeId));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar avaliações por usuário", description = "Retorna todas as avaliações feitas por um usuário específico.")
    public ResponseEntity<List<FilmeAvaliacaoDTO>> buscarAvaliacoesPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(filmeAvaliacaoService.buscarAvaliacoesPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação por ID", description = "Retorna os detalhes de uma avaliação específica.")
    public ResponseEntity<FilmeAvaliacaoDTO> buscarAvaliacaoPorId(@PathVariable Long id) {
        return filmeAvaliacaoService.buscarAvaliacaoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filme/{filmeId}/recentes")
    @Operation(summary = "Listar avaliações recentes do filme", description = "Retorna as avaliações mais recentes de um filme, com limite configurável.")
    public ResponseEntity<List<FilmeAvaliacaoDTO>> buscarAvaliacoesRecentes(
            @PathVariable Long filmeId,
            @RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(filmeAvaliacaoService.buscarAvaliacoesRecentesPorFilme(filmeId, limite));
    }

    @GetMapping("/filme/{filmeId}/mais-curtidas")
    @Operation(summary = "Listar avaliações mais curtidas do filme", description = "Retorna as avaliações com maior número de curtidas para um filme.")
    public ResponseEntity<List<FilmeAvaliacaoDTO>> buscarAvaliacoesMaisCurtidas(
            @PathVariable Long filmeId,
            @RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(filmeAvaliacaoService.buscarAvaliacoesMaisCurtidasPorFilme(filmeId, limite));
    }

    @GetMapping("/filme/{filmeId}/estatisticas")
    @Operation(summary = "Obter estatísticas do filme", description = "Retorna estatísticas gerais das avaliações do filme, como média, total, etc.")
    public ResponseEntity<Map<String, Object>> obterEstatisticasFilme(@PathVariable Long filmeId) {
        Map<String, Object> estatisticas = filmeAvaliacaoService.obterEstatisticasFilme(filmeId);
        return estatisticas.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(estatisticas);
    }

    @GetMapping("/verificar/{filmeId}/{usuarioId}")
    @Operation(summary = "Verificar se o usuário já avaliou o filme", description = "Retorna true/false se o usuário já possui avaliação para o filme.")
    public ResponseEntity<Map<String, Boolean>> verificarAvaliacaoExistente(
            @PathVariable Long filmeId,
            @PathVariable Long usuarioId) {
        boolean jaAvaliou = filmeAvaliacaoService.usuarioJaAvaliouFilme(filmeId, usuarioId);
        return ResponseEntity.ok(Map.of("ja_avaliou", jaAvaliou));
    }

    @PostMapping
    @Operation(summary = "Criar nova avaliação", description = "Cria uma nova avaliação para um filme.")
    public ResponseEntity<?> criarAvaliacao(@RequestBody FilmeAvaliacaoDTO avaliacaoDTO) {
        Optional<FilmeAvaliacaoDTO> created = filmeAvaliacaoService.criarAvaliacao(avaliacaoDTO);

        if (created.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    Map.of("erro", "Não foi possível criar a avaliação. Verifique se o filme e usuário existem.")
            );
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(created.get());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar avaliação", description = "Atualiza os dados de uma avaliação existente.")
    public ResponseEntity<FilmeAvaliacaoDTO> atualizarAvaliacao(
            @PathVariable Long id,
            @RequestBody FilmeAvaliacaoDTO avaliacaoDTO) {
        return filmeAvaliacaoService.atualizarAvaliacao(id, avaliacaoDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir avaliação", description = "Remove uma avaliação do sistema.")
    public ResponseEntity<Void> deletarAvaliacao(@PathVariable Long id) {
        return filmeAvaliacaoService.deletarAvaliacao(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/curtir")
    @Operation(summary = "Curtir avaliação", description = "Adiciona uma curtida à avaliação especificada.")
    public ResponseEntity<FilmeAvaliacaoDTO> curtirAvaliacao(@PathVariable Long id) {
        return filmeAvaliacaoService.curtirAvaliacao(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/descurtir")
    @Operation(summary = "Descurtir avaliação", description = "Remove uma curtida da avaliação especificada.")
    public ResponseEntity<FilmeAvaliacaoDTO> descurtirAvaliacao(@PathVariable Long id) {
        return filmeAvaliacaoService.descurtirAvaliacao(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
