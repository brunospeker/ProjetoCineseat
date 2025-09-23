package com.maisprati.Cineseat.controllers;

import com.maisprati.Cineseat.dto.FilmeDTO;
import com.maisprati.Cineseat.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/filmes")
@CrossOrigin(origins = "*")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    // GET /api/filmes - Buscar todos os filmes
    @GetMapping
    public ResponseEntity<List<FilmeDTO>> buscarTodosFilmes() {
        List<FilmeDTO> filmes = filmeService.buscarTodosFilmes();
        return ResponseEntity.ok(filmes);
    }

    // GET /api/filmes/em-cartaz - Buscar filmes em cartaz
    @GetMapping("/em-cartaz")
    public ResponseEntity<List<FilmeDTO>> buscarFilmesEmCartaz() {
        List<FilmeDTO> filmes = filmeService.buscarFilmesEmCartaz();
        return ResponseEntity.ok(filmes);
    }

    // GET /api/filmes/{id} - Buscar filme por ID
    @GetMapping("/{id}")
    public ResponseEntity<FilmeDTO> buscarFilmePorId(@PathVariable Long id) {
        Optional<FilmeDTO> filme = filmeService.buscarFilmePorId(id);
        return filme.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/filmes/ingresso/{ingressoId} - Buscar filme pelo ID da Ingresso.com
    @GetMapping("/ingresso/{ingressoId}")
    public ResponseEntity<FilmeDTO> buscarFilmePorIngressoId(@PathVariable String ingressoId) {
        Optional<FilmeDTO> filme = filmeService.buscarFilmePorIngressoId(ingressoId);
        return filme.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/filmes/buscar?titulo= - Buscar filmes por título
    @GetMapping("/buscar")
    public ResponseEntity<List<FilmeDTO>> buscarFilmesPorTitulo(@RequestParam String titulo) {
        List<FilmeDTO> filmes = filmeService.buscarFilmesPorTitulo(titulo);
        return ResponseEntity.ok(filmes);
    }

    // GET /api/filmes/genero/{genero} - Buscar filmes por gênero
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<FilmeDTO>> buscarFilmesPorGenero(@PathVariable String genero) {
        List<FilmeDTO> filmes = filmeService.buscarFilmesPorGenero(genero);
        return ResponseEntity.ok(filmes);
    }

    // POST /api/filmes - Criar novo filme
    @PostMapping
    public ResponseEntity<FilmeDTO> criarFilme(@RequestBody FilmeDTO filmeDTO) {
        try {
            FilmeDTO filmeCriado = filmeService.salvarFilme(filmeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(filmeCriado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /api/filmes/{id} - Atualizar filme
    @PutMapping("/{id}")
    public ResponseEntity<FilmeDTO> atualizarFilme(@PathVariable Long id, @RequestBody FilmeDTO filmeDTO) {
        Optional<FilmeDTO> filmeAtualizado = filmeService.atualizarFilme(id, filmeDTO);
        return filmeAtualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/filmes/{id} - Deletar filme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFilme(@PathVariable Long id) {
        boolean deletado = filmeService.deletarFilme(id);
        return deletado ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // POST /api/filmes/sincronizar - Sincronizar com API da Ingresso.com
    @PostMapping("/sincronizar")
    public ResponseEntity<String> sincronizarComIngressoAPI() {
        try {
            filmeService.sincronizarComIngressoAPI();
            return ResponseEntity.ok("Sincronização iniciada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao sincronizar: " + e.getMessage());
        }
    }
}