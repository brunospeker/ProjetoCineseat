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
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @GetMapping("/tmdb/{filmeId}")
    public ResponseEntity<FilmeDTO> buscarOuImportarFilme(@PathVariable String filmeId) {
        FilmeDTO filme = filmeService.buscarOuImportarFilme(filmeId);

        if (filme == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(filme);
    }

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

    // GET /api/filmes/{id} - Buscar filme por ID do banco
    @GetMapping("/{id}")
    public ResponseEntity<FilmeDTO> buscarFilmePorId(@PathVariable Long id) {
        Optional<FilmeDTO> filme = filmeService.buscarFilmePorId(id);
        return filme.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/filmes/filme/{filmeId} - Buscar filme pelo ID da TMDB
    @GetMapping("/filme/{filmeId}")
    public ResponseEntity<FilmeDTO> buscarFilmePorFilmeId(@PathVariable String filmeId) {
        Optional<FilmeDTO> filme = filmeService.buscarFilmePorFilmeId(filmeId);
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


    //POST /api/filmes/TMDB - Importar filme da API
    @PostMapping("/tmdb")
    public ResponseEntity<?> importarFilmeTMDB(@RequestBody FilmeDTO filmeDTO) {
        try {
            if (filmeDTO.getFilmeId() == null || filmeDTO.getFilmeId().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("filmeID é obrigatório para filmes da TMDB");
            }

            // Verifica se já existe
            if (filmeService.filmeExiste(filmeDTO.getFilmeId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Filme já existe no banco de dados com TMDB: " + filmeDTO.getFilmeId());
            }

            FilmeDTO filmeSalvo = filmeService.salvarFilmeTMDB(filmeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(filmeSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao importar filme: " + e.getMessage());
        }
    }

    //PUT /api/filmes/tmdb/sincronizar - Sincronizar filme da API
    //Se já existe, atualiza. Se não existe, cria.
    @PutMapping("/tmdb/sincronizar")
    public ResponseEntity<?> sincronizarFilmeTMDB(@RequestBody FilmeDTO filmeDTO) {
        try {
            if (filmeDTO.getFilmeId() == null || filmeDTO.getFilmeId().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("filmeID é obrigatório para filmes da TMDB");
            }

            FilmeDTO filmeSalvo = filmeService.salvarFilmeTMDB(filmeDTO);
            return ResponseEntity.ok(filmeSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao sincronizar filme: " + e.getMessage());
        }
    }


    //POST /api/filmes/tmdb/sincronizar-lote - Sincronizar múltiplos filmes
    //Importa/atualiza vários filmes da API de uma vez
    @PostMapping("/tmdb/sincronizar-lote")
    public ResponseEntity<?> sincronizarLoteTMDB(@RequestBody List<FilmeDTO> filmesDTO) {
        try {
            List<FilmeDTO> filmesSalvos = filmeService.sincronizarFilmesTMDB(filmesDTO);
            return ResponseEntity.ok(filmesSalvos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao sincronizar filmes: " + e.getMessage());
        }
    }

    //GET /api/filmes/api/existe/{filmeID} - Verifica se filme existe
    //Retorna true/false se o filme já está no banco
    @GetMapping("/tmdb/existe/{filmeID}")
    public ResponseEntity<Boolean> verificarExistencia(@PathVariable String filmeID) {
        boolean existe = filmeService.filmeExiste(filmeID);
        return ResponseEntity.ok(existe);
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