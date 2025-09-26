package com.maisprati.Cineseat.controllers;

import com.maisprati.Cineseat.dto.SalaAvaliacaoDTO;
import com.maisprati.Cineseat.service.SalaAvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SalaAvaliacaoController {

    @Autowired
    private SalaAvaliacaoService salaAvaliacaoService;

    // Criar nova avaliação para uma sala
    @PostMapping("/salas/{salaId}/avaliacoes")
    public ResponseEntity<SalaAvaliacaoDTO> criarAvaliacao(
            @PathVariable Long salaId,
            @RequestBody SalaAvaliacaoDTO salaAvaliacaoDTO) {
        try {
            // Define o salaId no DTO antes de passar para o service
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

    // Listar todas as avaliações de uma sala específica
    @GetMapping("/salas/{salaId}/avaliacoes")
    public ResponseEntity<List<SalaAvaliacaoDTO>> listarAvaliacoesPorSala(@PathVariable Long salaId) {
        try {
            List<SalaAvaliacaoDTO> avaliacoes = salaAvaliacaoService.buscarAvaliacoesPorSala(salaId);
            return new ResponseEntity<>(avaliacoes, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Buscar avaliação específica por ID
    @GetMapping("/avaliacoes-salas/{id}")
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

    // Listar todas as avaliações de salas
    @GetMapping("/avaliacoes-salas")
    public ResponseEntity<List<SalaAvaliacaoDTO>> listarTodasAvaliacoes() {
        List<SalaAvaliacaoDTO> avaliacoes = salaAvaliacaoService.buscarTodasAvaliacoes();
        return new ResponseEntity<>(avaliacoes, HttpStatus.OK);
    }

    // Atualizar avaliação existente
    @PutMapping("/avaliacoes-salas/{id}")
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

    // Deletar avaliação
    @DeleteMapping("/avaliacoes-salas/{id}")
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

    // Listar avaliações por usuário
    @GetMapping("/usuarios/{userId}/avaliacoes-salas")
    public ResponseEntity<List<SalaAvaliacaoDTO>> listarAvaliacoesPorUsuario(@PathVariable Long userId) {
        try {
            List<SalaAvaliacaoDTO> avaliacoes = salaAvaliacaoService.buscarAvaliacoesPorUsuario(userId);
            return new ResponseEntity<>(avaliacoes, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Buscar estatísticas detalhadas de uma sala
    @GetMapping("/salas/{salaId}/avaliacoes/estatisticas")
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

    // Buscar média de avaliações de uma sala (mantido para compatibilidade)
    @GetMapping("/salas/{salaId}/avaliacoes/media")
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

    // Curtir avaliação
    @PostMapping("/avaliacoes-salas/{id}/like")
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

    // Descurtir avaliação
    @PostMapping("/avaliacoes-salas/{id}/dislike")
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

    // Verificar se usuário já avaliou sala
    @GetMapping("/salas/{salaId}/usuarios/{userId}/ja-avaliou")
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