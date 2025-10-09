package com.maisprati.Cineseat.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maisprati.Cineseat.dto.CinemaAvaliacaoDTO;
import com.maisprati.Cineseat.entities.CinemaAvaliacao;
import com.maisprati.Cineseat.repositories.CinemaAvaliacaoRepository;

@Service
public class CinemaAvaliacaoService {

    @Autowired
    private CinemaAvaliacaoRepository avaliacaoRepository;

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

    public List<CinemaAvaliacaoDTO> findByCinemaId(Long idCinema) {
        return avaliacaoRepository.findByCinemaIdCinema(idCinema)
                .stream()
                .map(CinemaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaAvaliacaoDTO> findByUsuarioId(Long idUsuario) {
        return avaliacaoRepository.findByUsuarioId(idUsuario)
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

    public CinemaAvaliacaoDTO createAvaliacaoCinema(CinemaAvaliacao avaliacao) {
        avaliacao.setDataAvaliacao(LocalDateTime.now());
        CinemaAvaliacao newAvaliacao = avaliacaoRepository.save(avaliacao);
        return new CinemaAvaliacaoDTO(newAvaliacao);
    }

    public Optional<CinemaAvaliacaoDTO> updateAvaliacao(Long idAvaliacaoCinema, CinemaAvaliacaoDTO dto) {
        return avaliacaoRepository.findById(idAvaliacaoCinema).map(avaliacao -> {
            updateAvaliacaoData(avaliacao, dto);
            return new CinemaAvaliacaoDTO(avaliacaoRepository.save(avaliacao));
        });
    }

    private void updateAvaliacaoData(CinemaAvaliacao avaliacao, CinemaAvaliacaoDTO dto) {
        if (dto.getNotaGeral() != null) avaliacao.setNotaGeral(dto.getNotaGeral());
        if (dto.getNotaLimpeza() != null) avaliacao.setNotaLimpeza(dto.getNotaLimpeza());
        if (dto.getNotaAtendimento() != null) avaliacao.setNotaAtendimento(dto.getNotaAtendimento());
        if (dto.getNotaPreco() != null) avaliacao.setNotaPreco(dto.getNotaPreco());
        if (dto.getNotaAlimentacao() != null) avaliacao.setNotaAlimentacao(dto.getNotaAlimentacao());
        if (dto.getComentario() != null) avaliacao.setComentario(dto.getComentario());
        avaliacao.setDataAtualizacao(LocalDateTime.now());
    }

    public boolean deleteAvaliacao(Long idAvaliacaoCinema) {
        return avaliacaoRepository.findById(idAvaliacaoCinema).map(avaliacao -> {
            avaliacaoRepository.delete(avaliacao);
            return true;
        }).orElse(false);
    }
}