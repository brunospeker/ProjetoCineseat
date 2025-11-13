package com.maisprati.Cineseat.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maisprati.Cineseat.dto.CinemaDTO;
import com.maisprati.Cineseat.entities.Cinema;
import com.maisprati.Cineseat.entities.Sala;
import com.maisprati.Cineseat.repositories.CinemaRepository;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    public List<CinemaDTO> findAll() {
        return cinemaRepository.findAll()
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<CinemaDTO> findById(Long idCinema) {
        return cinemaRepository.findById(idCinema).map(CinemaDTO::new);
    }

    public List<CinemaDTO> findByCidade(String cidade) {
        return cinemaRepository.findByCidadeContainingIgnoreCase(cidade)
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> findByEstado(String estado) {
        return cinemaRepository.findByEstadoContainingIgnoreCase(estado)
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> findByBairro(String bairro) {
        return cinemaRepository.findByBairroContainingIgnoreCase(bairro)
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> findByAtivoTrue() {
        return cinemaRepository.findAllByAtivoTrue()
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> findByTemBomboniereTrue() {
        return cinemaRepository.findAllByTemBomboniereTrue()
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> findByTotalSalas(Integer totalSalas) {
        return cinemaRepository.findByTotalSalas(totalSalas)
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> findByTotalSalasBetween(Integer min, Integer max) {
        return cinemaRepository.findByTotalSalasBetween(min, max)
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> findByMediaGeral(Double mediaGeral) {
        return cinemaRepository.findByMediaGeral(mediaGeral)
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public CinemaDTO createCinema(Cinema cinema) {
        if (cinema.getSalas() != null) {
            for (Sala sala : cinema.getSalas()) {
                sala.setCinema(cinema);
            }
        }
        cinema.atualizarTotalSalas();
        cinema.setDataCriacao(LocalDateTime.now());
        cinema.setMediaGeral(0.0);

        Cinema newCinema = cinemaRepository.save(cinema);
        return new CinemaDTO(newCinema);
    }

    public Optional<CinemaDTO> updateCinema(Long idCinema, CinemaDTO dto) {
        return cinemaRepository.findById(idCinema).map(cinema -> {
            updateCinemaData(cinema, dto);
            return new CinemaDTO(cinemaRepository.save(cinema));
        });
    }

    private void updateCinemaData(Cinema cinema, CinemaDTO dto) {
        if (dto.getNomeCinema() != null) cinema.setNomeCinema(dto.getNomeCinema());
        if (dto.getSite() != null) cinema.setSite(dto.getSite());
        if (dto.getCnpj() != null) cinema.setCnpj(dto.getCnpj());
        if (dto.getEstado() != null) cinema.setEstado(dto.getEstado());
        if (dto.getUf() != null) cinema.setUf(dto.getUf());
        if (dto.getCidade() != null) cinema.setCidade(dto.getCidade());
        if (dto.getIdCidade() != null) cinema.setIdCidade(dto.getIdCidade());
        if (dto.getBairro() != null) cinema.setBairro(dto.getBairro());
        if (dto.getNumero() != null) cinema.setNumero(dto.getNumero());
        if (dto.getImagensJson() != null) cinema.setImagensJson(dto.getImagensJson());
        if (dto.getTemBomboniere() != null) cinema.setTemBomboniere(dto.getTemBomboniere());
        if (dto.getTotalSalas() != null) cinema.setTotalSalas(dto.getTotalSalas());
        if (dto.getMediaGeral() != null) cinema.setMediaGeral(dto.getMediaGeral());
        if (dto.getAtivo() != null) cinema.setAtivo(dto.getAtivo());

        cinema.setDataAtualizacao(LocalDateTime.now());
    }

    public boolean deleteCinema(Long idCinema) {
        return cinemaRepository.findById(idCinema).map(cinema -> {
            cinemaRepository.delete(cinema);
            return true;
        }).orElse(false);
    }

    public List<CinemaDTO> rankingGeral() {
        return cinemaRepository.rankingCinemas()
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> rankingPorCidade(String cidade) {
        return cinemaRepository.rankingCinemasByCity(cidade)
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> rankingPorEstado(String estado) {
        return cinemaRepository.rankingCinemasByState(estado)
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }
}
