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


    //POST /api/filmes/local - Criar filme local explicitamente
    //Para filmes criados manualmente no sistema
    @PostMapping("/local")
    public ResponseEntity<FilmeDTO> criarFilmeLocal(@RequestBody FilmeDTO filmeDTO) {
        try {
            FilmeDTO filmeCriado = filmeService.salvarFilmeLocal(filmeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(filmeCriado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    //POST /api/filmes/api - Importar filme da API
    //Falha se o filme já existir (retorna 409 Conflict)
    @PostMapping("/api")
    public ResponseEntity<?> importarFilmeAPI(@RequestBody FilmeDTO filmeDTO) {
        try {
            if (filmeDTO.getIngressoId() == null || filmeDTO.getIngressoId().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("IngressoId é obrigatório para filmes da API");
            }

            // Verifica se já existe
            if (filmeService.filmeExiste(filmeDTO.getIngressoId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Filme já existe no banco de dados com IngressoId: " + filmeDTO.getIngressoId());
            }

            FilmeDTO filmeSalvo = filmeService.salvarFilmeAPI(filmeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(filmeSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao importar filme: " + e.getMessage());
        }
    }

    //PUT /api/filmes/api/sincronizar - Sincronizar filme da API
    //Se já existe, atualiza. Se não existe, cria.
    @PutMapping("/api/sincronizar")
    public ResponseEntity<?> sincronizarFilmeAPI(@RequestBody FilmeDTO filmeDTO) {
        try {
            if (filmeDTO.getIngressoId() == null || filmeDTO.getIngressoId().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("IngressoId é obrigatório para filmes da API");
            }

            FilmeDTO filmeSalvo = filmeService.salvarFilmeAPI(filmeDTO);
            return ResponseEntity.ok(filmeSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao sincronizar filme: " + e.getMessage());
        }
    }


    //POST /api/filmes/api/sincronizar-lote - Sincronizar múltiplos filmes
    //Importa/atualiza vários filmes da API de uma vez
    @PostMapping("/api/sincronizar-lote")
    public ResponseEntity<?> sincronizarLoteAPI(@RequestBody List<FilmeDTO> filmesDTO) {
        try {
            List<FilmeDTO> filmesSalvos = filmeService.sincronizarFilmesAPI(filmesDTO);
            return ResponseEntity.ok(filmesSalvos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao sincronizar filmes: " + e.getMessage());
        }
    }

    //GET /api/filmes/api/existe/{ingressoId} - Verifica se filme existe
    //Retorna true/false se o filme já está no banco
    @GetMapping("/api/existe/{ingressoId}")
    public ResponseEntity<Boolean> verificarExistencia(@PathVariable String ingressoId) {
        boolean existe = filmeService.filmeExiste(ingressoId);
        return ResponseEntity.ok(existe);
    }

    //GET /api/filmes/api/buscar-ou-criar - Busca ou cria filme da API
    //Retorna o filme existente ou cria um novo
    @PostMapping("/api/buscar-ou-criar")
    public ResponseEntity<?> buscarOuCriarFilmeAPI(@RequestBody FilmeDTO filmeDTO) {
        try {
            FilmeDTO filme = filmeService.buscarOuCriarFilmeAPI(filmeDTO);
            return ResponseEntity.ok(filme);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar ou criar filme: " + e.getMessage());
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

}