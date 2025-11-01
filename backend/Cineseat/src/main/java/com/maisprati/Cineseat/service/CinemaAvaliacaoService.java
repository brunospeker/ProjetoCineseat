package com.maisprati.Cineseat.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maisprati.Cineseat.dto.CinemaAvaliacaoDTO;
import com.maisprati.Cineseat.entities.Cinema;
import com.maisprati.Cineseat.entities.CinemaAvaliacao;
import com.maisprati.Cineseat.entities.User;
import com.maisprati.Cineseat.repositories.CinemaAvaliacaoRepository;
import com.maisprati.Cineseat.repositories.CinemaRepository;
import com.maisprati.Cineseat.repositories.UserRepository;

@Service
public class CinemaAvaliacaoService {

    @Autowired
    private CinemaAvaliacaoRepository avaliacaoRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CinemaAvaliacaoDTO> findAll() {
        return avaliacaoRepository.findAll()
                .stream()
                .map(CinemaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<CinemaAvaliacaoDTO> findById(Long idAvaliacaoCinema) {
        return avaliacaoRepository.findById(idAvaliacaoCinema)
                .map(CinemaAvaliacaoDTO::new);
    }

    public List<CinemaAvaliacaoDTO> findByCinema(Long idCinema) {
        return avaliacaoRepository.findByCinema_IdCinema(idCinema)
                .stream()
                .map(CinemaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaAvaliacaoDTO> findByUsuario(Long idUsuario) {
        return avaliacaoRepository.findByUsuario_Id(idUsuario)
                .stream()
                .map(CinemaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaAvaliacaoDTO> findByUsuarioAndCinema(Long idUsuario, Long idCinema) {
        return avaliacaoRepository.findByUsuario_IdAndCinema_IdCinema(idUsuario, idCinema)
                .stream()
                .map(CinemaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaAvaliacaoDTO> findByCinemaWithComentario(Long idCinema) {
        return avaliacaoRepository.findByCinema_IdCinemaAndComentarioIsNotNull(idCinema)
                .stream()
                .map(CinemaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaAvaliacaoDTO> findByNotaGeral(Integer notaGeral) {
        return avaliacaoRepository.findByNotaGeral(notaGeral)
                .stream()
                .map(CinemaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaAvaliacaoDTO> findByNotaLimpeza(Integer notaLimpeza) {
        return avaliacaoRepository.findByNotaLimpeza(notaLimpeza)
                .stream()
                .map(CinemaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaAvaliacaoDTO> findByNotaPreco(Integer notaPreco) {
        return avaliacaoRepository.findByNotaPreco(notaPreco)
                .stream()
                .map(CinemaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaAvaliacaoDTO> findByNotaAlimentacao(Integer notaAlimentacao) {
        return avaliacaoRepository.findByNotaAlimentacao(notaAlimentacao)
                .stream()
                .map(CinemaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaAvaliacaoDTO> findByNotaAtendimento(Integer notaAtendimento) {
        return avaliacaoRepository.findByNotaAtendimento(notaAtendimento)
                .stream()
                .map(CinemaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public Long countByCinema(Long idCinema) {
        return avaliacaoRepository.countByCinema_IdCinema(idCinema);
    }

    public Long countByUsuario(Long idUsuario) {
        return avaliacaoRepository.countByUsuario_Id(idUsuario);
    }

    public List<CinemaAvaliacaoDTO> findAvaliacoesRecentes(Long idCinema) {
        return avaliacaoRepository.findAvaliacoesRecentes(idCinema)
                .stream()
                .map(CinemaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<CinemaAvaliacaoDTO> findAverageRatingsByCinemaId(Long idCinema) {
        return avaliacaoRepository.findAverageRatingsByCinemaId(idCinema);
    }

    public List<CinemaAvaliacaoDTO> rankingByAverageScore() {
        return avaliacaoRepository.rankingTopCinemasByAverageScore();
    }

    public List<CinemaAvaliacaoDTO> rankingByCity(String cidade) {
        return avaliacaoRepository.rankingTopCinemasByCity(cidade);
    }

    public List<CinemaAvaliacaoDTO> rankingByState(String estado) {
        return avaliacaoRepository.rankingTopCinemasByState(estado);
    }

    @Transactional
    public CinemaAvaliacaoDTO create(CinemaAvaliacaoDTO dto) {
        if (dto.getIdCinema() == null) {
            throw new IllegalArgumentException("idCinema é obrigatório");
        }
        if (dto.getIdUsuario() == null) {
            throw new IllegalArgumentException("idUsuario é obrigatório");
        }

        Cinema cinemaRef = cinemaRepository.getReferenceById(dto.getIdCinema());
        User userRef = userRepository.getReferenceById(dto.getIdUsuario());

        CinemaAvaliacao avaliacao = new CinemaAvaliacao();
        avaliacao.setCinema(cinemaRef);
        avaliacao.setUsuario(userRef);
        avaliacao.setIdIngresso(dto.getIdIngresso());
        avaliacao.setNotaGeral(dto.getNotaGeral());
        avaliacao.setNotaLimpeza(dto.getNotaLimpeza());
        avaliacao.setNotaAtendimento(dto.getNotaAtendimento());
        avaliacao.setNotaPreco(dto.getNotaPreco());
        avaliacao.setNotaAlimentacao(dto.getNotaAlimentacao());
        avaliacao.setComentario(dto.getComentario());
        avaliacao.setDataAvaliacao(LocalDateTime.now());

        avaliacao = avaliacaoRepository.save(avaliacao);
        return new CinemaAvaliacaoDTO(avaliacao);
    }

    @Transactional
    public Optional<CinemaAvaliacaoDTO> update(Long idAvaliacaoCinema, CinemaAvaliacaoDTO dto) {
        return avaliacaoRepository.findById(idAvaliacaoCinema).map(avaliacao -> {
            if (dto.getNotaGeral() != null) avaliacao.setNotaGeral(dto.getNotaGeral());
            if (dto.getNotaLimpeza() != null) avaliacao.setNotaLimpeza(dto.getNotaLimpeza());
            if (dto.getNotaAtendimento() != null) avaliacao.setNotaAtendimento(dto.getNotaAtendimento());
            if (dto.getNotaPreco() != null) avaliacao.setNotaPreco(dto.getNotaPreco());
            if (dto.getNotaAlimentacao() != null) avaliacao.setNotaAlimentacao(dto.getNotaAlimentacao());
            if (dto.getComentario() != null) avaliacao.setComentario(dto.getComentario());
            avaliacao.setDataAtualizacao(LocalDateTime.now());
            avaliacao = avaliacaoRepository.save(avaliacao);
            return new CinemaAvaliacaoDTO(avaliacao);
        });
    }

    @Transactional
    public boolean delete(Long idAvaliacaoCinema) {
        return avaliacaoRepository.findById(idAvaliacaoCinema).map(avaliacao -> {
            avaliacaoRepository.delete(avaliacao);
            return true;
        }).orElse(false);
    }
}