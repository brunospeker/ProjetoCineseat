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

    // Buscar filme por ID da Ingresso.com
    public Optional<FilmeDTO> buscarFilmePorIngressoId(String ingressoId) {
        return filmeRepository.findByIngressoId(ingressoId)
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


    //Verifica se um filme já existe no banco pelo ingressoId
    public boolean filmeExiste(String ingressoId) {
        return filmeRepository.existsByIngressoId(ingressoId);
    }


    //Salva filme da API com validação de duplicatas
    //Se já existir, atualiza. Se não existir, cria novo.

    @Transactional
    public FilmeDTO salvarFilmeAPI(FilmeDTO filmeDTO) {
        if (filmeDTO.getIngressoId() == null || filmeDTO.getIngressoId().isEmpty()) {
            throw new IllegalArgumentException("Filme da API deve ter ingressoId");
        }

        // Verifica se já existe
        Optional<Filme> filmeExistente = filmeRepository.findByIngressoId(filmeDTO.getIngressoId());

        if (filmeExistente.isPresent()) {
            // Atualiza o filme existente
            Filme filme = filmeExistente.get();
            atualizarDadosFilme(filme, filmeDTO);
            filme.setOrigem(Filme.OrigemFilme.API);
            Filme atualizado = filmeRepository.save(filme);
            return convertToDTO(atualizado);
        } else {
            // Cria novo filme da API
            Filme filme = convertToEntity(filmeDTO);
            filme.setOrigem(Filme.OrigemFilme.API);
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

    //Busca ou cria filme da API
    //Útil para importação: retorna existente ou cria novo

    @Transactional
    public FilmeDTO buscarOuCriarFilmeAPI(FilmeDTO filmeDTO) {
        if (filmeDTO.getIngressoId() == null || filmeDTO.getIngressoId().isEmpty()) {
            throw new IllegalArgumentException("Filme da API deve ter ingressoId");
        }

        Optional<Filme> existente = filmeRepository.findByIngressoId(filmeDTO.getIngressoId());

        if (existente.isPresent()) {
            return convertToDTO(existente.get());
        } else {
            Filme filme = convertToEntity(filmeDTO);
            filme.setOrigem(Filme.OrigemFilme.API);
            Filme salvo = filmeRepository.save(filme);
            return convertToDTO(salvo);
        }
    }


    //Sincroniza múltiplos filmes da API de uma vez
    //Ideal para importação em lote

    @Transactional
    public List<FilmeDTO> sincronizarFilmesAPI(List<FilmeDTO> filmesDTO) {
        return filmesDTO.stream()
                .map(this::salvarFilmeAPI)
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
        dto.setIngressoId(filme.getIngressoId());
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

        // Usa construtor apropriado se tiver ingressoId
        if (dto.getIngressoId() != null && !dto.getIngressoId().isEmpty()) {
            filme = new Filme(dto.getIngressoId(), dto.getTitulo());
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