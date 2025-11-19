package com.maisprati.Cineseat.controllers;

import com.maisprati.Cineseat.dto.SalaAvaliacaoDTO;
import com.maisprati.Cineseat.service.SalaAvaliacaoService;

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
@RequestMapping("/api")
@Tag(name = "Avaliações de Salas", description = "Operações relacionadas às avaliações feitas em salas")
public class SalaAvaliacaoController {

    @Autowired
    private SalaAvaliacaoService salaAvaliacaoService;

    @PostMapping("/salas/{salaId}/avaliacoes")
    @Operation(summary = "Criar nova avaliação", description = "Cria uma nova avaliação para uma sala específica.")
    public ResponseEntity<SalaAvaliacaoDTO> criarAvaliacao(
            @PathVariable Long salaId,
            @RequestBody SalaAvaliacaoDTO salaAvaliacaoDTO) {
        try {
            salaAvaliacaoDTO.setSalaId(salaId);

            Optional<SalaAvaliacaoDTO> novaAvaliacao = salaAvaliacaoService.criarAvaliacao(salaAvaliacaoDTO);

            if (novaAvaliacao.isPresent()) {
                return new ResponseEntity<>(novaAvaliacao.get(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/salas/{salaId}/avaliacoes")
    @Operation(summary = "Listar avaliações por sala", description = "Retorna todas as avaliações feitas para uma sala específica.")
    public ResponseEntity<List<SalaAvaliacaoDTO>> listarAvaliacoesPorSala(@PathVariable Long salaId) {
        try {
            List<SalaAvaliacaoDTO> avaliacoes = salaAvaliacaoService.buscarAvaliacoesPorSala(salaId);
            return new ResponseEntity<>(avaliacoes, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/avaliacoes-salas/{id}")
    @Operation(summary = "Buscar avaliação por ID", description = "Retorna os detalhes de uma avaliação específica de sala.")
    public ResponseEntity<SalaAvaliacaoDTO> buscarAvaliacaoPorId(@PathVariable Long id) {
        try {
            Optional<SalaAvaliacaoDTO> avaliacao = salaAvaliacaoService.buscarAvaliacaoPorId(id);

            if (avaliacao.isPresent()) {
                return new ResponseEntity<>(avaliacao.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/avaliacoes-salas")
    @Operation(summary = "Listar todas as avaliações", description = "Retorna todas as avaliações registradas de salas.")
    public ResponseEntity<List<SalaAvaliacaoDTO>> listarTodasAvaliacoes() {
        List<SalaAvaliacaoDTO> avaliacoes = salaAvaliacaoService.buscarTodasAvaliacoes();
        return new ResponseEntity<>(avaliacoes, HttpStatus.OK);
    }

    @PutMapping("/avaliacoes-salas/{id}")
    @Operation(summary = "Atualizar avaliação", description = "Atualiza os dados de uma avaliação existente de sala.")
    public ResponseEntity<SalaAvaliacaoDTO> atualizarAvaliacao(
            @PathVariable Long id,
            @RequestBody SalaAvaliacaoDTO salaAvaliacaoDTO) {
        try {
            Optional<SalaAvaliacaoDTO> avaliacaoAtualizada = salaAvaliacaoService.atualizarAvaliacao(id, salaAvaliacaoDTO);

            if (avaliacaoAtualizada.isPresent()) {
                return new ResponseEntity<>(avaliacaoAtualizada.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/avaliacoes-salas/{id}")
    @Operation(summary = "Excluir avaliação", description = "Remove uma avaliação de sala do sistema.")
    public ResponseEntity<Void> deletarAvaliacao(@PathVariable Long id) {
        try {
            boolean deletada = salaAvaliacaoService.deletarAvaliacao(id);

            if (deletada) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/usuarios/{userId}/avaliacoes-salas")
    @Operation(summary = "Listar avaliações por usuário", description = "Retorna todas as avaliações de salas realizadas por um usuário.")
    public ResponseEntity<List<SalaAvaliacaoDTO>> listarAvaliacoesPorUsuario(@PathVariable Long userId) {
        try {
            List<SalaAvaliacaoDTO> avaliacoes = salaAvaliacaoService.buscarAvaliacoesPorUsuario(userId);
            return new ResponseEntity<>(avaliacoes, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/salas/{salaId}/avaliacoes/estatisticas")
    @Operation(summary = "Estatísticas da sala", description = "Retorna estatísticas detalhadas das avaliações de uma sala, como média, total, etc.")
    public ResponseEntity<Map<String, Object>> obterEstatisticasSala(@PathVariable Long salaId) {
        try {
            Map<String, Object> estatisticas = salaAvaliacaoService.obterEstatisticasSala(salaId);

            if (estatisticas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(estatisticas, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/salas/{salaId}/avaliacoes/media")
    @Operation(summary = "Média geral da sala", description = "Retorna apenas a média geral das avaliações da sala.")
    public ResponseEntity<Double> obterMediaAvaliacoes(@PathVariable Long salaId) {
        try {
            Map<String, Object> estatisticas = salaAvaliacaoService.obterEstatisticasSala(salaId);

            if (estatisticas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Double media = (Double) estatisticas.get("media_geral");
            return new ResponseEntity<>(media, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/avaliacoes-salas/{id}/like")
    @Operation(summary = "Curtir avaliação", description = "Adiciona uma curtida à avaliação de sala especificada.")
    public ResponseEntity<SalaAvaliacaoDTO> curtirAvaliacao(@PathVariable Long id) {
        try {
            Optional<SalaAvaliacaoDTO> avaliacao = salaAvaliacaoService.curtirAvaliacao(id);

            if (avaliacao.isPresent()) {
                return new ResponseEntity<>(avaliacao.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/avaliacoes-salas/{id}/dislike")
    @Operation(summary = "Descurtir avaliação", description = "Remove uma curtida da avaliação de sala especificada.")
    public ResponseEntity<SalaAvaliacaoDTO> descurtirAvaliacao(@PathVariable Long id) {
        try {
            Optional<SalaAvaliacaoDTO> avaliacao = salaAvaliacaoService.descurtirAvaliacao(id);

            if (avaliacao.isPresent()) {
                return new ResponseEntity<>(avaliacao.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/salas/{salaId}/usuarios/{userId}/ja-avaliou")
    @Operation(summary = "Verificar se usuário já avaliou", description = "Retorna true/false indicando se o usuário já avaliou aquela sala.")
    public ResponseEntity<Boolean> verificarSeUsuarioJaAvaliou(
            @PathVariable Long salaId,
            @PathVariable Long userId) {
        try {
            boolean jaAvaliou = salaAvaliacaoService.usuarioJaAvaliouSala(salaId, userId);
            return new ResponseEntity<>(jaAvaliou, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
