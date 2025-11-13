package com.maisprati.Cineseat.controllers;

import com.maisprati.Cineseat.dto.FilmeAvaliacaoDTO;
import com.maisprati.Cineseat.service.FilmeAvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/filme-avaliacoes")
public class FilmeAvaliacaoController {

    @Autowired
    private FilmeAvaliacaoService filmeAvaliacaoService;

    // GET /api/filme-avaliacoes - Buscar todas as avaliações
    @GetMapping
    public ResponseEntity<List<FilmeAvaliacaoDTO>> buscarTodasAvaliacoes() {
        List<FilmeAvaliacaoDTO> avaliacoes = filmeAvaliacaoService.buscarTodasAvaliacoes();
        return ResponseEntity.ok(avaliacoes);
    }

    // GET /api/filme-avaliacoes/filme/{filmeId} - Buscar avaliações por filme
    @GetMapping("/filme/{filmeId}")
    public ResponseEntity<List<FilmeAvaliacaoDTO>> buscarAvaliacoesPorFilme(@PathVariable Long filmeId) {
        List<FilmeAvaliacaoDTO> avaliacoes = filmeAvaliacaoService.buscarAvaliacoesPorFilme(filmeId);
        return ResponseEntity.ok(avaliacoes);
    }

    // GET /api/filme-avaliacoes/usuario/{usuarioId} - Buscar avaliações por usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<FilmeAvaliacaoDTO>> buscarAvaliacoesPorUsuario(@PathVariable Long usuarioId) {
        List<FilmeAvaliacaoDTO> avaliacoes = filmeAvaliacaoService.buscarAvaliacoesPorUsuario(usuarioId);
        return ResponseEntity.ok(avaliacoes);
    }

    // GET /api/filme-avaliacoes/{id} - Buscar avaliação por ID
    @GetMapping("/{id}")
    public ResponseEntity<FilmeAvaliacaoDTO> buscarAvaliacaoPorId(@PathVariable Long id) {
        Optional<FilmeAvaliacaoDTO> avaliacao = filmeAvaliacaoService.buscarAvaliacaoPorId(id);
        return avaliacao.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/filme-avaliacoes/filme/{filmeId}/recentes - Buscar avaliações recentes
    @GetMapping("/filme/{filmeId}/recentes")
    public ResponseEntity<List<FilmeAvaliacaoDTO>> buscarAvaliacoesRecentes(
            @PathVariable Long filmeId,
            @RequestParam(defaultValue = "10") int limite) {
        List<FilmeAvaliacaoDTO> avaliacoes = filmeAvaliacaoService.buscarAvaliacoesRecentesPorFilme(filmeId, limite);
        return ResponseEntity.ok(avaliacoes);
    }

    // GET /api/filme-avaliacoes/filme/{filmeId}/mais-curtidas - Buscar avaliações mais curtidas
    @GetMapping("/filme/{filmeId}/mais-curtidas")
    public ResponseEntity<List<FilmeAvaliacaoDTO>> buscarAvaliacoesMaisCurtidas(
            @PathVariable Long filmeId,
            @RequestParam(defaultValue = "10") int limite) {
        List<FilmeAvaliacaoDTO> avaliacoes = filmeAvaliacaoService.buscarAvaliacoesMaisCurtidasPorFilme(filmeId, limite);
        return ResponseEntity.ok(avaliacoes);
    }

    // GET /api/filme-avaliacoes/filme/{filmeId}/estatisticas - Obter estatísticas do filme
    @GetMapping("/filme/{filmeId}/estatisticas")
    public ResponseEntity<Map<String, Object>> obterEstatisticasFilme(@PathVariable Long filmeId) {
        Map<String, Object> estatisticas = filmeAvaliacaoService.obterEstatisticasFilme(filmeId);
        if (estatisticas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estatisticas);
    }

    // GET /api/filme-avaliacoes/verificar/{filmeId}/{usuarioId} - Verificar se usuário já avaliou
    @GetMapping("/verificar/{filmeId}/{usuarioId}")
    public ResponseEntity<Map<String, Boolean>> verificarAvaliacaoExistente(
            @PathVariable Long filmeId,
            @PathVariable Long usuarioId) {
        boolean jaAvaliou = filmeAvaliacaoService.usuarioJaAvaliouFilme(filmeId, usuarioId);
        return ResponseEntity.ok(Map.of("ja_avaliou", jaAvaliou));
    }

    // POST /api/filme-avaliacoes - Criar nova avaliação
    @PostMapping
    public ResponseEntity<?> criarAvaliacao(@RequestBody FilmeAvaliacaoDTO avaliacaoDTO) {
        Optional<FilmeAvaliacaoDTO> avaliacaoCriada = filmeAvaliacaoService.criarAvaliacao(avaliacaoDTO);

        if (avaliacaoCriada.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Não foi possível criar a avaliação. Verifique se o filme e usuário existem e se o usuário já não avaliou este filme."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoCriada.get());
    }

    // PUT /api/filme-avaliacoes/{id} - Atualizar avaliação
    @PutMapping("/{id}")
    public ResponseEntity<FilmeAvaliacaoDTO> atualizarAvaliacao(
            @PathVariable Long id,
            @RequestBody FilmeAvaliacaoDTO avaliacaoDTO) {
        Optional<FilmeAvaliacaoDTO> avaliacaoAtualizada = filmeAvaliacaoService.atualizarAvaliacao(id, avaliacaoDTO);
        return avaliacaoAtualizada.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/filme-avaliacoes/{id} - Deletar avaliação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAvaliacao(@PathVariable Long id) {
        boolean deletada = filmeAvaliacaoService.deletarAvaliacao(id);
        return deletada ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // POST /api/filme-avaliacoes/{id}/curtir - Curtir avaliação
    @PostMapping("/{id}/curtir")
    public ResponseEntity<FilmeAvaliacaoDTO> curtirAvaliacao(@PathVariable Long id) {
        Optional<FilmeAvaliacaoDTO> avaliacaoAtualizada = filmeAvaliacaoService.curtirAvaliacao(id);
        return avaliacaoAtualizada.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/filme-avaliacoes/{id}/descurtir - Descurtir avaliação
    @PostMapping("/{id}/descurtir")
    public ResponseEntity<FilmeAvaliacaoDTO> descurtirAvaliacao(@PathVariable Long id) {
        Optional<FilmeAvaliacaoDTO> avaliacaoAtualizada = filmeAvaliacaoService.descurtirAvaliacao(id);
        return avaliacaoAtualizada.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}