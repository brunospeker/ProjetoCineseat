package com.maisprati.Cineseat.controllers;

import com.maisprati.Cineseat.dto.SalaDTO;
import com.maisprati.Cineseat.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/salas")
@CrossOrigin(origins = "*")
public class SalaController {

    @Autowired
    private SalaService salaService;

    // GET /api/salas - Buscar todas as salas
    @GetMapping
    public ResponseEntity<List<SalaDTO>> buscarTodasSalas() {
        List<SalaDTO> salas = salaService.buscarTodasSalas();
        return ResponseEntity.ok(salas);
    }

    // GET /api/salas/ativas - Buscar salas ativas
    @GetMapping("/ativas")
    public ResponseEntity<List<SalaDTO>> buscarSalasAtivas() {
        List<SalaDTO> salas = salaService.buscarSalasAtivas();
        return ResponseEntity.ok(salas);
    }

    // GET /api/salas/{id} - Buscar sala por ID
    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> buscarSalaPorId(@PathVariable Long id) {
        Optional<SalaDTO> sala = salaService.buscarSalaPorId(id);
        return sala.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/salas - Criar nova sala
    @PostMapping
    public ResponseEntity<SalaDTO> criarSala(@RequestBody SalaDTO salaDTO) {
        try {
            // Verificar se já existe sala com esse ingresso_id
            if (salaDTO.getIngressoId() != null) {
                Optional<SalaDTO> salaExistente = salaService.buscarPorIngressoId(salaDTO.getIngressoId());
                if (salaExistente.isPresent()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(null); // Ou retornar uma mensagem de erro
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
    public ResponseEntity<SalaDTO> buscarPorIngressoId(@PathVariable String ingressoId) {
        Optional<SalaDTO> sala = salaService.buscarPorIngressoId(ingressoId);
        return sala.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/salas/numero/{numeroSala} - Buscar sala por número
    @GetMapping("/numero/{numeroSala}")
    public ResponseEntity<SalaDTO> buscarSalaPorNumero(@PathVariable Integer numeroSala) {
        Optional<SalaDTO> sala = salaService.buscarSalaPorNumero(numeroSala);
        return sala.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/salas/tipo-tela/{tipoTela} - Buscar salas por tipo de tela
    @GetMapping("/tipo-tela/{tipoTela}")
    public ResponseEntity<List<SalaDTO>> buscarSalasPorTipoTela(@PathVariable String tipoTela) {
        List<SalaDTO> salas = salaService.buscarSalasPorTipoTela(tipoTela);
        return ResponseEntity.ok(salas);
    }

    // GET /api/salas/capacidade/{capacidadeMinima} - Buscar salas por capacidade mínima
    @GetMapping("/capacidade/{capacidadeMinima}")
    public ResponseEntity<List<SalaDTO>> buscarSalasPorCapacidade(@PathVariable Integer capacidadeMinima) {
        List<SalaDTO> salas = salaService.buscarSalasPorCapacidade(capacidadeMinima);
        return ResponseEntity.ok(salas);
    }

    // GET /api/salas/acessiveis - Buscar salas acessíveis
    @GetMapping("/acessiveis")
    public ResponseEntity<List<SalaDTO>> buscarSalasAcessiveis() {
        List<SalaDTO> salas = salaService.buscarSalasAcessiveis();
        return ResponseEntity.ok(salas);
    }

    // GET /api/salas/buscar?nome= - Buscar salas por nome
    @GetMapping("/buscar")
    public ResponseEntity<List<SalaDTO>> buscarSalasPorNome(@RequestParam String nome) {
        List<SalaDTO> salas = salaService.buscarSalasPorNome(nome);
        return ResponseEntity.ok(salas);
    }


    // PUT /api/salas/{id} - Atualizar sala
    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> atualizarSala(@PathVariable Long id, @RequestBody SalaDTO salaDTO) {
        Optional<SalaDTO> salaAtualizada = salaService.atualizarSala(id, salaDTO);
        return salaAtualizada.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/salas/{id} - Deletar sala
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSala(@PathVariable Long id) {
        boolean deletada = salaService.deletarSala(id);
        return deletada ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // PATCH /api/salas/{id}/status - Ativar/Desativar sala
    @PatchMapping("/{id}/status")
    public ResponseEntity<SalaDTO> alterarStatusSala(@PathVariable Long id, @RequestParam Boolean ativa) {
        Optional<SalaDTO> salaAtualizada = salaService.alterarStatusSala(id, ativa);
        return salaAtualizada.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}