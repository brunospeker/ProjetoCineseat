package com.maisprati.Cineseat.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maisprati.Cineseat.dto.FilmeDTO;
import com.maisprati.Cineseat.entities.Filme;
import com.maisprati.Cineseat.repositories.FilmeRepository;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private TMDBService tmdbService;


    public FilmeDTO buscarOuImportarFilme(String filmeId) {
        // Tenta buscar no banco de dados
        Optional<Filme> filmeExistente = filmeRepository.findByFilmeId(filmeId);

        if (filmeExistente.isPresent()) {
            // Se existir no BD, retorna
            return convertToDTO(filmeExistente.get());
        }

        // Se não existir, busca na API do TMDB
        FilmeDTO filmeTMDB = tmdbService.buscarFilmePorId(filmeId);

        if (filmeTMDB == null) {
            // Se não encontrar no TMDB também, retorna null
            return null;
        }

        // Salva o filme no banco de dados
        Filme filme = convertToEntity(filmeTMDB);
        filme.setOrigem(Filme.OrigemFilme.TMDB);
        Filme filmeSalvo = filmeRepository.save(filme);

        // Retorna o DTO do filme salvo
        return convertToDTO(filmeSalvo);
    }


    // Buscar todos os filmes
    public List<FilmeDTO> buscarTodosFilmes() {
        return filmeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar filmes em cartaz
    public List<FilmeDTO> buscarFilmesEmCartaz() {
        return filmeRepository.findByEmCartazTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar filme por ID
    public Optional<FilmeDTO> buscarFilmePorId(Long id) {
        return filmeRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Buscar filme por ID da TMDB (NO BANCO)
    public Optional<FilmeDTO> buscarFilmePorFilmeId(String filmeID) {
        return filmeRepository.findByFilmeId(filmeID)
                .map(this::convertToDTO);
    }

    // Buscar filmes por título
    public List<FilmeDTO> buscarFilmesPorTitulo(String titulo) {
        return filmeRepository.findByTituloContainingIgnoreCase(titulo)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar filmes por gênero
    public List<FilmeDTO> buscarFilmesPorGenero(String genero) {
        return filmeRepository.findByGeneroContainingIgnoreCase(genero)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Salvar filme
    @Transactional
    public FilmeDTO salvarFilme(FilmeDTO filmeDTO) {
        Filme filme = convertToEntity(filmeDTO);
        Filme filmeSalvo = filmeRepository.save(filme);
        return convertToDTO(filmeSalvo);
    }


    //Verifica se um filme já existe no banco pelo filmeId
    public boolean filmeExiste(String filmeId) {
        return filmeRepository.existsByFilmeId(filmeId);
    }


    // Salva filme do TMDB com validação de duplicatas
    @Transactional
    public FilmeDTO salvarFilmeTMDB(FilmeDTO filmeDTO) {
        if (filmeDTO.getFilmeId() == null || filmeDTO.getFilmeId().isEmpty()) {
            throw new IllegalArgumentException("Filme da TMDB deve ter filmeID");
        }

        // Verifica se já existe
        Optional<Filme> filmeExistente = filmeRepository.findByFilmeId(filmeDTO.getFilmeId());

        if (filmeExistente.isPresent()) {
            // Atualiza o filme existente
            Filme filme = filmeExistente.get();
            atualizarDadosFilme(filme, filmeDTO);
            filme.setOrigem(Filme.OrigemFilme.TMDB);
            Filme atualizado = filmeRepository.save(filme);
            return convertToDTO(atualizado);
        } else {
            // Cria novo filme da API
            Filme filme = convertToEntity(filmeDTO);
            filme.setOrigem(Filme.OrigemFilme.TMDB);
            Filme salvo = filmeRepository.save(filme);
            return convertToDTO(salvo);
        }
    }


    //Salva filme criado localmente

    @Transactional
    public FilmeDTO salvarFilmeLocal(FilmeDTO filmeDTO) {
        Filme filme = convertToEntity(filmeDTO);
        filme.setOrigem(Filme.OrigemFilme.LOCAL);
        Filme salvo = filmeRepository.save(filme);
        return convertToDTO(salvo);
    }

    // Sincroniza múltiplos filmes do TMDB de uma vez
    @Transactional
    public List<FilmeDTO> sincronizarFilmesTMDB(List<FilmeDTO> filmesDTO) {
        return filmesDTO.stream()
                .map(this::salvarFilmeTMDB)
                .collect(Collectors.toList());
    }

    // Atualizar filme
    public Optional<FilmeDTO> atualizarFilme(Long id, FilmeDTO filmeDTO) {
        return filmeRepository.findById(id)
                .map(filme -> {
                    atualizarDadosFilme(filme, filmeDTO);
                    Filme filmeAtualizado = filmeRepository.save(filme);
                    return convertToDTO(filmeAtualizado);
                });
    }

    // Deletar filme
    public boolean deletarFilme(Long id) {
        if (filmeRepository.existsById(id)) {
            filmeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Converter Entity para DTO
    private FilmeDTO convertToDTO(Filme filme) {
        FilmeDTO dto = new FilmeDTO();
        dto.setId(filme.getId());
        dto.setFilmeId(filme.getFilmeId());
        dto.setTitulo(filme.getTitulo());
        dto.setSinopse(filme.getSinopse());
        dto.setDiretor(filme.getDiretor());
        dto.setDataLancamento(filme.getDataLancamento());
        dto.setDuracao(filme.getDuracao());
        dto.setGenero(filme.getGenero());
        dto.setClassificacao(filme.getClassificacao());
        dto.setPosterUrl(filme.getPosterUrl());
        dto.setTrailerUrl(filme.getTrailerUrl());
        dto.setNota(filme.getNota());
        dto.setEmCartaz(filme.getEmCartaz());

        if (filme.getOrigem() != null) {
            dto.setOrigem(filme.getOrigem().name());
        }

        return dto;
    }

    // Converter DTO para Entity
    private Filme convertToEntity(FilmeDTO dto) {
        Filme filme;

        // Usa construtor apropriado se tiver filmeID
        if (dto.getFilmeId() != null && !dto.getFilmeId().isEmpty()) {
            filme = new Filme(dto.getFilmeId(), dto.getTitulo());
        } else {
            filme = new Filme();
        }

        filme.setId(dto.getId());
        filme.setTitulo(dto.getTitulo());
        filme.setSinopse(dto.getSinopse());
        filme.setDiretor(dto.getDiretor());
        filme.setDataLancamento(dto.getDataLancamento());
        filme.setDuracao(dto.getDuracao());
        filme.setGenero(dto.getGenero());
        filme.setClassificacao(dto.getClassificacao());
        filme.setPosterUrl(dto.getPosterUrl());
        filme.setTrailerUrl(dto.getTrailerUrl());
        filme.setNota(dto.getNota());
        filme.setEmCartaz(dto.getEmCartaz());
        return filme;
    }

    // Atualizar dados do filme existente
    private void atualizarDadosFilme(Filme filme, FilmeDTO dto) {
        if (dto.getTitulo() != null) filme.setTitulo(dto.getTitulo());
        if (dto.getSinopse() != null) filme.setSinopse(dto.getSinopse());
        if (dto.getDiretor() != null) filme.setDiretor(dto.getDiretor());
        if (dto.getDataLancamento() != null) filme.setDataLancamento(dto.getDataLancamento());
        if (dto.getDuracao() != null) filme.setDuracao(dto.getDuracao());
        if (dto.getGenero() != null) filme.setGenero(dto.getGenero());
        if (dto.getClassificacao() != null) filme.setClassificacao(dto.getClassificacao());
        if (dto.getPosterUrl() != null) filme.setPosterUrl(dto.getPosterUrl());
        if (dto.getTrailerUrl() != null) filme.setTrailerUrl(dto.getTrailerUrl());
        if (dto.getNota() != null) filme.setNota(dto.getNota());
        if (dto.getEmCartaz() != null) filme.setEmCartaz(dto.getEmCartaz());
    }
}