package com.maisprati.Cineseat.controllers;

import com.maisprati.Cineseat.dto.PoltronaDTO;
import com.maisprati.Cineseat.service.PoltronaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/poltronas")
@Tag(name = "Poltronas", description = "Operações relacionadas às poltronas cadastradas nas salas de cinema")
public class PoltronaController {

    @Autowired
    private PoltronaService poltronaService;

    @GetMapping
    @Operation(summary = "Listar todas as poltronas", description = "Retorna todas as poltronas cadastradas no sistema.")
    public ResponseEntity<List<PoltronaDTO>> findAll() {
        return ResponseEntity.ok(poltronaService.findAll());
    }

    @GetMapping("/{idPoltrona}")
    @Operation(summary = "Buscar poltrona por ID", description = "Retorna os detalhes de uma poltrona específica.")
    public ResponseEntity<PoltronaDTO> findById(@PathVariable Long idPoltrona) {
        return ResponseEntity.ok(poltronaService.findById(idPoltrona));
    }

    @GetMapping("/sala/{idSala}")
    @Operation(summary = "Listar poltronas de uma sala", description = "Retorna todas as poltronas pertencentes à sala informada.")
    public ResponseEntity<List<PoltronaDTO>> findBySala(@PathVariable Long idSala) {
        return ResponseEntity.ok(poltronaService.findBySala(idSala));
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Listar poltronas por tipo", description = "Retorna todas as poltronas do tipo informado (ex: 0 = NORMAL, 1 = VIP, etc).")
    public ResponseEntity<List<PoltronaDTO>> findByTipo(@PathVariable int tipo) {
        return ResponseEntity.ok(poltronaService.findByTipo(tipo));
    }

    @GetMapping("media/sala/{idSala}")
    @Operation(summary = "Calcular média geral da sala", description = "Retorna a média geral das poltronas de uma sala com base nas médias individuais.")
    public ResponseEntity<Double> calcularMediaSala(@PathVariable Long idSala) {
        return ResponseEntity.ok(poltronaService.calcularMediaSala(idSala));
    }


    @GetMapping("/ranking/{idSala}")
    @Operation(summary = "Ranking de poltronas por sala", description = "Retorna as poltronas ordenadas pela maior média geral dentro da sala informada.")
    public ResponseEntity<List<PoltronaDTO>> getRankingBySala(@PathVariable Long idSala) {
        return ResponseEntity.ok(poltronaService.getRankingBySala(idSala));
    }


    @PostMapping
    @Operation(summary = "Cadastrar nova poltrona", description = "Cria uma nova poltrona vinculada a uma sala existente.")
    public ResponseEntity<PoltronaDTO> create(@RequestBody PoltronaDTO dto) {
        return ResponseEntity.ok(poltronaService.create(dto));
    }

    @PutMapping("/{idPoltrona}")
    @Operation(summary = "Atualizar poltrona", description = "Atualiza as informações de uma poltrona existente.")
    public ResponseEntity<PoltronaDTO> update(@PathVariable Long idPoltrona, @RequestBody PoltronaDTO dto) {
        return ResponseEntity.ok(poltronaService.update(idPoltrona, dto));
    }

    @DeleteMapping("/{idPoltrona}")
    @Operation(summary = "Excluir poltrona", description = "Remove uma poltrona do sistema com base no ID informado.")
    public ResponseEntity<Void> delete(@PathVariable Long idPoltrona) {
        poltronaService.delete(idPoltrona);
        return ResponseEntity.noContent().build();
    }
}
