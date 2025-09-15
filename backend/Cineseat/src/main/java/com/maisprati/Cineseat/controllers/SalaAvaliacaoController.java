package com.malsprati.cineseat.controllers;

import com.maisprati.cineseat.dto.SalaAvaliacaoDTO;
import com.maisprati.cineseat.service.SalaAvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
            @Valid @RequestBody SalaAvaliacaoDTO salaAvaliacaoDTO) {
        try {
            SalaAvaliacaoDTO novaAvaliacao = salaAvaliacaoService.criarAvaliacao(salaId, salaAvaliacaoDTO);
            return new ResponseEntity<>(novaAvaliacao, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Listar todas as avaliações de uma sala específica
    @GetMapping("/salas/{salaId}/avaliacoes")
    public ResponseEntity<List<SalaAvaliacaoDTO>> listarAvaliacoesPorSala(@PathVariable Long salaId) {
        try {
            List<SalaAvaliacaoDTO> avaliacoes = salaAvaliacaoService.listarAvaliacoesPorSala(salaId);
            return new ResponseEntity<>(avaliacoes, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Buscar avaliação específica por ID
    @GetMapping("/avaliacoes-salas/{id}")
    public ResponseEntity<SalaAvaliacaoDTO> buscarAvaliacaoPorId(@PathVariable Long id) {
        try {
            SalaAvaliacaoDTO avaliacao = salaAvaliacaoService.buscarPorId(id);
            return new ResponseEntity<>(avaliacao, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Listar todas as avaliações de salas
    @GetMapping("/avaliacoes-salas")
    public ResponseEntity<List<SalaAvaliacaoDTO>> listarTodasAvaliacoes() {
        List<SalaAvaliacaoDTO> avaliacoes = salaAvaliacaoService.listarTodas();
        return new ResponseEntity<>(avaliacoes, HttpStatus.OK);
    }

    // Atualizar avaliação existente
    @PutMapping("/avaliacoes-salas/{id}")
    public ResponseEntity<SalaAvaliacaoDTO> atualizarAvaliacao(
            @PathVariable Long id,
            @Valid @RequestBody SalaAvaliacaoDTO salaAvaliacaoDTO) {
        try {
            SalaAvaliacaoDTO avaliacaoAtualizada = salaAvaliacaoService.atualizarAvaliacao(id, salaAvaliacaoDTO);
            return new ResponseEntity<>(avaliacaoAtualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Deletar avaliação
    @DeleteMapping("/avaliacoes-salas/{id}")
    public ResponseEntity<Void> deletarAvaliacao(@PathVariable Long id) {
        try {
            salaAvaliacaoService.deletarAvaliacao(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Listar avaliações por usuário
    @GetMapping("/usuarios/{userId}/avaliacoes-salas")
    public ResponseEntity<List<SalaAvaliacaoDTO>> listarAvaliacoesPorUsuario(@PathVariable Long userId) {
        try {
            List<SalaAvaliacaoDTO> avaliacoes = salaAvaliacaoService.listarAvaliacoesPorUsuario(userId);
            return new ResponseEntity<>(avaliacoes, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Buscar média de avaliações de uma sala
    @GetMapping("/salas/{salaId}/avaliacoes/media")
    public ResponseEntity<Double> obterMediaAvaliacoes(@PathVariable Long salaId) {
        try {
            Double media = salaAvaliacaoService.calcularMediaAvaliacoes(salaId);
            return new ResponseEntity<>(media, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}