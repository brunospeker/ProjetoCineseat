package com.maisprati.Cineseat.controllers;

import com.maisprati.Cineseat.dto.FilmeDTO;
import com.maisprati.Cineseat.service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/filmes")
@Tag(name = "Filmes", description = "Operações relacionadas aos filmes cadastrados no sistema ou importados da TMDB")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @GetMapping("/tmdb/{filmeId}")
    @Operation(summary = "Buscar ou importar filme da TMDB", description = "Busca um filme pelo ID da TMDB. Caso ele não exista no banco, será importado automaticamente.")
    public ResponseEntity<FilmeDTO> buscarOuImportarFilme(@PathVariable String filmeId) {
        FilmeDTO filme = filmeService.buscarOuImportarFilme(filmeId);

        if (filme == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(filme);
    }

    @GetMapping
    @Operation(summary = "Listar todos os filmes", description = "Retorna todos os filmes cadastrados no banco.")
    public ResponseEntity<List<FilmeDTO>> buscarTodosFilmes() {
        List<FilmeDTO> filmes = filmeService.buscarTodosFilmes();
        return ResponseEntity.ok(filmes);
    }

    @GetMapping("/em-cartaz")
    @Operation(summary = "Listar filmes em cartaz", description = "Retorna todos os filmes que estão marcados como em cartaz.")
    public ResponseEntity<List<FilmeDTO>> buscarFilmesEmCartaz() {
        List<FilmeDTO> filmes = filmeService.buscarFilmesEmCartaz();
        return ResponseEntity.ok(filmes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar filme por ID", description = "Busca um filme no banco pelo seu ID interno.")
    public ResponseEntity<FilmeDTO> buscarFilmePorId(@PathVariable Long id) {
        Optional<FilmeDTO> filme = filmeService.buscarFilmePorId(id);
        return filme.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filme/{filmeId}")
    @Operation(summary = "Buscar filme pelo ID da TMDB", description = "Busca um filme no banco utilizando o ID oficial da TMDB.")
    public ResponseEntity<FilmeDTO> buscarFilmePorFilmeId(@PathVariable String filmeId) {
        Optional<FilmeDTO> filme = filmeService.buscarFilmePorFilmeId(filmeId);
        return filme.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar filmes por título", description = "Realiza uma busca filtrando os filmes pelo título informado.")
    public ResponseEntity<List<FilmeDTO>> buscarFilmesPorTitulo(@RequestParam String titulo) {
        List<FilmeDTO> filmes = filmeService.buscarFilmesPorTitulo(titulo);
        return ResponseEntity.ok(filmes);
    }

    @GetMapping("/genero/{genero}")
    @Operation(summary = "Buscar filmes por gênero",description = "Lista filmes filtrando pelo gênero informado.")
    public ResponseEntity<List<FilmeDTO>> buscarFilmesPorGenero(@PathVariable String genero) {
        List<FilmeDTO> filmes = filmeService.buscarFilmesPorGenero(genero);
        return ResponseEntity.ok(filmes);
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo filme", description = "Cria manualmente um novo filme no banco de dados.")
    public ResponseEntity<FilmeDTO> criarFilme(@RequestBody FilmeDTO filmeDTO) {
        try {
            FilmeDTO filmeCriado = filmeService.salvarFilme(filmeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(filmeCriado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/local")
    @Operation(summary = "Criar filme local", description = "Cria um filme manualmente no sistema sem vínculo com a TMDB.")
    public ResponseEntity<FilmeDTO> criarFilmeLocal(@RequestBody FilmeDTO filmeDTO) {
        try {
            FilmeDTO filmeCriado = filmeService.salvarFilmeLocal(filmeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(filmeCriado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/tmdb")
    @Operation(summary = "Importar filme da TMDB", description = "Importa um filme da TMDB usando o filmeId. Caso já exista, retorna erro.")
    public ResponseEntity<?> importarFilmeTMDB(@RequestBody FilmeDTO filmeDTO) {
        try {
            if (filmeDTO.getFilmeId() == null || filmeDTO.getFilmeId().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("filmeID é obrigatório para filmes da TMDB");
            }

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

    @PutMapping("/tmdb/sincronizar")
    @Operation(summary = "Sincronizar filme da TMDB", description = "Atualiza os dados de um filme da TMDB caso exista, ou cria um novo caso não exista.")
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

    @PostMapping("/tmdb/sincronizar-lote")
    @Operation(summary = "Sincronizar lote de filmes da TMDB", description = "Importa ou atualiza vários filmes da TMDB em uma única requisição.")
    public ResponseEntity<?> sincronizarLoteTMDB(@RequestBody List<FilmeDTO> filmesDTO) {
        try {
            List<FilmeDTO> filmesSalvos = filmeService.sincronizarFilmesTMDB(filmesDTO);
            return ResponseEntity.ok(filmesSalvos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao sincronizar filmes: " + e.getMessage());
        }
    }

    @GetMapping("/tmdb/existe/{filmeID}")
    @Operation(summary = "Verificar existência do filme", description = "Retorna true/false indicando se um filme com filmeId da TMDB já está cadastrado no banco.")
    public ResponseEntity<Boolean> verificarExistencia(@PathVariable String filmeID) {
        boolean existe = filmeService.filmeExiste(filmeID);
        return ResponseEntity.ok(existe);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar filme", description = "Atualiza as informações de um filme existente pelo ID.")
    public ResponseEntity<FilmeDTO> atualizarFilme(@PathVariable Long id, @RequestBody FilmeDTO filmeDTO) {
        Optional<FilmeDTO> filmeAtualizado = filmeService.atualizarFilme(id, filmeDTO);
        return filmeAtualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir filme", description = "Remove um filme do banco de dados pelo ID informado.")
    public ResponseEntity<Void> deletarFilme(@PathVariable Long id) {
        boolean deletado = filmeService.deletarFilme(id);
        return deletado ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
