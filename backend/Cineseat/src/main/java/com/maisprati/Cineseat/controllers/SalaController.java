package com.maisprati.Cineseat.controllers;

import com.maisprati.Cineseat.dto.SalaDTO;
import com.maisprati.Cineseat.service.SalaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/salas")
@Tag(name = "Salas", description = "Operações relacionadas às salas de cinema")
public class SalaController {

    @Autowired
    private SalaService salaService;

    // GET /api/salas - Buscar todas as salas
    @GetMapping
    @Operation(summary = "Listar todas as salas", description = "Retorna uma lista com todas as salas cadastradas.")
    public ResponseEntity<List<SalaDTO>> buscarTodasSalas() {
        List<SalaDTO> salas = salaService.buscarTodasSalas();
        return ResponseEntity.ok(salas);
    }

    // GET /api/salas/ativas - Buscar salas ativas
    @GetMapping("/ativas")
    @Operation(summary = "Listar salas ativas", description = "Retorna apenas as salas que estão ativas.")
    public ResponseEntity<List<SalaDTO>> buscarSalasAtivas() {
        List<SalaDTO> salas = salaService.buscarSalasAtivas();
        return ResponseEntity.ok(salas);
    }

    // GET /api/salas/{id} - Buscar sala por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar sala por ID", description = "Retorna os dados da sala correspondente ao ID informado.")
    public ResponseEntity<SalaDTO> buscarSalaPorId(@PathVariable Long id) {
        Optional<SalaDTO> sala = salaService.buscarSalaPorId(id);
        return sala.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/salas - Criar nova sala
    @PostMapping
    @Operation(summary = "Criar nova sala", description = "Cria uma nova sala no sistema.")
    public ResponseEntity<SalaDTO> criarSala(@RequestBody SalaDTO salaDTO) {
        try {

            if (salaDTO.getIngressoId() != null) {
                Optional<SalaDTO> salaExistente = salaService.buscarPorIngressoId(salaDTO.getIngressoId());
                if (salaExistente.isPresent()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
                }
            }

            SalaDTO salaCriada = salaService.salvarSala(salaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(salaCriada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /api/salas/ingresso/{ingressoId}
    @GetMapping("/ingresso/{ingressoId}")
    @Operation(summary = "Buscar sala por ingressoId", description = "Retorna a sala associada ao ingressoId informado.")
    public ResponseEntity<SalaDTO> buscarPorIngressoId(@PathVariable String ingressoId) {
        Optional<SalaDTO> sala = salaService.buscarPorIngressoId(ingressoId);
        return sala.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/salas/numero/{numeroSala} - Buscar sala por número
    @GetMapping("/numero/{numeroSala}")
    @Operation(summary = "Buscar sala por número", description = "Retorna uma sala pelo número da sala.")
    public ResponseEntity<SalaDTO> buscarSalaPorNumero(@PathVariable Integer numeroSala) {
        Optional<SalaDTO> sala = salaService.buscarSalaPorNumero(numeroSala);
        return sala.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/salas/tipo-tela/{tipoTela} - Buscar salas por tipo de tela
    @GetMapping("/tipo-tela/{tipoTela}")
    @Operation(summary = "Buscar salas por tipo de tela", description = "Retorna todas as salas compatíveis com o tipo de tela informado.")
    public ResponseEntity<List<SalaDTO>> buscarSalasPorTipoTela(@PathVariable String tipoTela) {
        List<SalaDTO> salas = salaService.buscarSalasPorTipoTela(tipoTela);
        return ResponseEntity.ok(salas);
    }

    // GET /api/salas/capacidade/{capacidadeMinima} - Buscar salas por capacidade mínima
    @GetMapping("/capacidade/{capacidadeMinima}")
    @Operation(summary = "Buscar salas por capacidade mínima", description = "Retorna todas as salas com capacidade igual ou superior ao valor informado.")
    public ResponseEntity<List<SalaDTO>> buscarSalasPorCapacidade(@PathVariable Integer capacidadeMinima) {
        List<SalaDTO> salas = salaService.buscarSalasPorCapacidade(capacidadeMinima);
        return ResponseEntity.ok(salas);
    }

    // GET /api/salas/acessiveis - Buscar salas acessíveis
    @GetMapping("/acessiveis")
    @Operation(summary = "Listar salas acessíveis", description = "Retorna salas com estrutura acessível.")
    public ResponseEntity<List<SalaDTO>> buscarSalasAcessiveis() {
        List<SalaDTO> salas = salaService.buscarSalasAcessiveis();
        return ResponseEntity.ok(salas);
    }

    // GET /api/salas/buscar?nome= - Buscar salas por nome
    @GetMapping("/buscar")
    @Operation(summary = "Buscar salas por nome", description = "Permite buscar salas pelo nome com filtro de texto.")
    public ResponseEntity<List<SalaDTO>> buscarSalasPorNome(@RequestParam String nome) {
        List<SalaDTO> salas = salaService.buscarSalasPorNome(nome);
        return ResponseEntity.ok(salas);
    }

    // GET /api/salas/cinema/{idCinema}
    @GetMapping("/cinema/{idCinema}")
    @Operation(summary = "Listar salas por cinema", description = "Retorna todas as salas pertencentes ao cinema informado.")
    public ResponseEntity<List<SalaDTO>> buscarSalasPorCinema(@PathVariable Long idCinema) {
        List<SalaDTO> salas = salaService.buscarSalasPorCinema(idCinema);
        return ResponseEntity.ok(salas);
    }

    // PUT /api/salas/{id} - Atualizar sala
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar sala", description = "Atualiza os dados de uma sala existente.")
    public ResponseEntity<SalaDTO> atualizarSala(@PathVariable Long id, @RequestBody SalaDTO salaDTO) {
        Optional<SalaDTO> salaAtualizada = salaService.atualizarSala(id, salaDTO);
        return salaAtualizada.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/salas/{id} - Deletar sala
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar sala", description = "Remove uma sala do sistema.")
    public ResponseEntity<Void> deletarSala(@PathVariable Long id) {
        boolean deletada = salaService.deletarSala(id);
        return deletada ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // PATCH /api/salas/{id}/status - Ativar/Desativar sala
    @PatchMapping("/{id}/status")
    @Operation(summary = "Alterar status da sala", description = "Ativa ou desativa uma sala.")
    public ResponseEntity<SalaDTO> alterarStatusSala(@PathVariable Long id, @RequestParam Boolean ativa) {
        Optional<SalaDTO> salaAtualizada = salaService.alterarStatusSala(id, ativa);
        return salaAtualizada.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
