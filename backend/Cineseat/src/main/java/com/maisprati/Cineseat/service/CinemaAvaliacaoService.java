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
        avaliacao.setNotaLimpeza(dto.getNotaLimpeza());
        avaliacao.setNotaAtendimento(dto.getNotaAtendimento());
        avaliacao.setNotaPreco(dto.getNotaPreco());
        avaliacao.setNotaAlimentacao(dto.getNotaAlimentacao());
        avaliacao.setComentario(dto.getComentario());

        avaliacao = avaliacaoRepository.save(avaliacao);

        atualizarMediaGeralCinema(cinemaRef);

        return new CinemaAvaliacaoDTO(avaliacao);
    }
    @Transactional
    public Optional<CinemaAvaliacaoDTO> update(Long idAvaliacaoCinema, CinemaAvaliacaoDTO dto) {
        return avaliacaoRepository.findById(idAvaliacaoCinema).map(avaliacao -> {
            if (dto.getNotaLimpeza() != null) avaliacao.setNotaLimpeza(dto.getNotaLimpeza());
            if (dto.getNotaAtendimento() != null) avaliacao.setNotaAtendimento(dto.getNotaAtendimento());
            if (dto.getNotaPreco() != null) avaliacao.setNotaPreco(dto.getNotaPreco());
            if (dto.getNotaAlimentacao() != null) avaliacao.setNotaAlimentacao(dto.getNotaAlimentacao());
            if (dto.getComentario() != null) avaliacao.setComentario(dto.getComentario());
            avaliacao.setDataAtualizacao(LocalDateTime.now());

            avaliacao = avaliacaoRepository.save(avaliacao);

            atualizarMediaGeralCinema(avaliacao.getCinema());

            return new CinemaAvaliacaoDTO(avaliacao);
        });
    }

    @Transactional
    public boolean delete(Long idAvaliacaoCinema) {
        return avaliacaoRepository.findById(idAvaliacaoCinema).map(avaliacao -> {
            Cinema cinema = avaliacao.getCinema();
            avaliacaoRepository.delete(avaliacao);
            atualizarMediaGeralCinema(cinema);
            return true;
        }).orElse(false);
    }

    private void atualizarMediaGeralCinema(Cinema cinema) {
        Double novaMedia = avaliacaoRepository.findMediaGeralByCinemaId(cinema.getIdCinema())
                .orElse(0.0);

        cinema.setMediaGeral(novaMedia);
        cinemaRepository.saveAndFlush(cinema);
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

    public Long countByCinema(Long idCinema) {
        return avaliacaoRepository.countByCinema_IdCinema(idCinema);
    }

    public Long countByUsuario(Long idUsuario) {
        return avaliacaoRepository.countByUsuario_Id(idUsuario);
    }

    public Optional<Double> getMediaGeralByCinemaId(Long idCinema) {
        return avaliacaoRepository.findMediaGeralByCinemaId(idCinema);
    }

}
