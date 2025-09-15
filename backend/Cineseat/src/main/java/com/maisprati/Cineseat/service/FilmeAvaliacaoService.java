package com.maisprati.cineseat.service;

import com.maisprati.cineseat.dto.FilmeAvaliacaoDTO;
import com.maisprati.cineseat.entities.FilmeAvaliacao;
import com.maisprati.cineseat.entities.Filme;
import com.maisprati.cineseat.entities.User;
import com.maisprati.cineseat.repositories.FilmeAvaliacaoRepository;
import com.maisprati.cineseat.repositories.FilmeRepository;
import com.maisprati.cineseat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmeAvaliacaoService {

    @Autowired
    private FilmeAvaliacaoRepository filmeAvaliacaoRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private UserRepository userRepository;

    // Buscar todas as avaliações
    public List<FilmeAvaliacaoDTO> buscarTodasAvaliacoes() {
        return filmeAvaliacaoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar avaliações por filme
    public List<FilmeAvaliacaoDTO> buscarAvaliacoesPorFilme(Long filmeId) {
        Optional<Filme> filme = filmeRepository.findById(filmeId);
        if (filme.isPresent()) {
            return filmeAvaliacaoRepository.findByFilmeAndAtivaTrue(filme.get())
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    // Buscar avaliações por usuário
    public List<FilmeAvaliacaoDTO> buscarAvaliacoesPorUsuario(Long usuarioId) {
        Optional<User> usuario = userRepository.findById(usuarioId);
        if (usuario.isPresent()) {
            return filmeAvaliacaoRepository.findByUsuarioAndAtivaTrue(usuario.get())
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    // Buscar avaliação específica
    public Optional<FilmeAvaliacaoDTO> buscarAvaliacaoPorId(Long id) {
        return filmeAvaliacaoRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Verificar se usuário já avaliou filme
    public boolean usuarioJaAvaliouFilme(Long filmeId, Long usuarioId) {
        Optional<Filme> filme = filmeRepository.findById(filmeId);
        Optional<User> usuario = userRepository.findById(usuarioId);

        if (filme.isPresent() && usuario.isPresent()) {
            return filmeAvaliacaoRepository.existsByFilmeAndUsuarioAndAtivaTrue(filme.get(), usuario.get());
        }
        return false;
    }

    // Criar nova avaliação
    public Optional<FilmeAvaliacaoDTO> criarAvaliacao(FilmeAvaliacaoDTO avaliacaoDTO) {
        // Verificar se filme e usuário existem
        Optional<Filme> filme = filmeRepository.findById(avaliacaoDTO.getFilmeId());
        Optional<User> usuario = userRepository.findById(avaliacaoDTO.getUsuarioId());

        if (filme.isEmpty() || usuario.isEmpty()) {
            return Optional.empty();
        }

        // Verificar se usuário já avaliou este filme
        if (usuarioJaAvaliouFilme(avaliacaoDTO.getFilmeId(), avaliacaoDTO.getUsuarioId())) {
            return Optional.empty();
        }

        FilmeAvaliacao avaliacao = new FilmeAvaliacao();
        avaliacao.setFilme(filme.get());
        avaliacao.setUsuario(usuario.get());
        avaliacao.setNota(avaliacaoDTO.getNota());
        avaliacao.setComentario(avaliacaoDTO.getComentario());

        FilmeAvaliacao avaliacaoSalva = filmeAvaliacaoRepository.save(avaliacao);
        return Optional.of(convertToDTO(avaliacaoSalva));
    }

    // Atualizar avaliação
    public Optional<FilmeAvaliacaoDTO> atualizarAvaliacao(Long id, FilmeAvaliacaoDTO avaliacaoDTO) {
        return filmeAvaliacaoRepository.findById(id)
                .map(avaliacao -> {
                    if (avaliacaoDTO.getNota() != null) avaliacao.setNota(avaliacaoDTO.getNota());
                    if (avaliacaoDTO.getComentario() != null) avaliacao.setComentario(avaliacaoDTO.getComentario());

                    FilmeAvaliacao avaliacaoAtualizada = filmeAvaliacaoRepository.save(avaliacao);
                    return convertToDTO(avaliacaoAtualizada);
                });
    }

    // Deletar avaliação (soft delete)
    public boolean deletarAvaliacao(Long id) {
        return filmeAvaliacaoRepository.findById(id)
                .map(avaliacao -> {
                    avaliacao.setAtiva(false);
                    filmeAvaliacaoRepository.save(avaliacao);
                    return true;
                }).orElse(false);
    }

    // Curtir avaliação
    public Optional<FilmeAvaliacaoDTO> curtirAvaliacao(Long id) {
        return filmeAvaliacaoRepository.findById(id)
                .map(avaliacao -> {
                    avaliacao.incrementarLikes();
                    FilmeAvaliacao avaliacaoAtualizada = filmeAvaliacaoRepository.save(avaliacao);
                    return convertToDTO(avaliacaoAtualizada);
                });
    }

    // Descurtir avaliação
    public Optional<FilmeAvaliacaoDTO> descurtirAvaliacao(Long id) {
        return filmeAvaliacaoRepository.findById(id)
                .map(avaliacao -> {
                    avaliacao.incrementarDislikes();
                    FilmeAvaliacao avaliacaoAtualizada = filmeAvaliacaoRepository.save(avaliacao);
                    return convertToDTO(avaliacaoAtualizada);
                });
    }

    // Obter estatísticas de um filme
    public Map<String, Object> obterEstatisticasFilme(Long filmeId) {
        Optional<Filme> filme = filmeRepository.findById(filmeId);
        if (filme.isEmpty()) {
            return Map.of();
        }

        Double media = filmeAvaliacaoRepository.calcularMediaAvaliacoesPorFilme(filme.get());
        Long total = filmeAvaliacaoRepository.contarAvaliacoesPorFilme(filme.get());
        List<Object[]> distribuicaoNotas = filmeAvaliacaoRepository.obterEstatisticasNotasPorFilme(filme.get());

        return Map.of(
                "media", media != null ? media : 0.0,
                "total", total,
                "distribuicao_notas", distribuicaoNotas
        );
    }

    // Buscar avaliações mais recentes de um filme
    public List<FilmeAvaliacaoDTO> buscarAvaliacoesRecentesPorFilme(Long filmeId, int limite) {
        Optional<Filme> filme = filmeRepository.findById(filmeId);
        if (filme.isEmpty()) {
            return List.of();
        }

        return filmeAvaliacaoRepository.findAvaliacoesRecentesPorFilme(filme.get())
                .stream()
                .limit(limite)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar avaliações mais curtidas de um filme
    public List<FilmeAvaliacaoDTO> buscarAvaliacoesMaisCurtidasPorFilme(Long filmeId, int limite) {
        Optional<Filme> filme = filmeRepository.findById(filmeId);
        if (filme.isEmpty()) {
            return List.of();
        }

        return filmeAvaliacaoRepository.findAvaliacoesMaisCurtidasPorFilme(filme.get())
                .stream()
                .limit(limite)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Converter Entity para DTO
    private FilmeAvaliacaoDTO convertToDTO(FilmeAvaliacao avaliacao) {
        FilmeAvaliacaoDTO dto = new FilmeAvaliacaoDTO();
        dto.setId(avaliacao.getId());
        dto.setFilmeId(avaliacao.getFilme().getId());
        dto.setFilmeTitulo(avaliacao.getFilme().getTitulo());
        dto.setUsuarioId(avaliacao.getUsuario().getId());
        dto.setUsuarioNome(avaliacao.getUsuario().getName());
        dto.setNota(avaliacao.getNota());
        dto.setComentario(avaliacao.getComentario());
        dto.setDataCriacao(avaliacao.getDataCriacao());
        dto.setDataAtualizacao(avaliacao.getDataAtualizacao());
        dto.setAtiva(avaliacao.getAtiva());
        dto.setLikesCount(avaliacao.getLikesCount());
        dto.setDislikesCount(avaliacao.getDislikesCount());
        return dto;
    }