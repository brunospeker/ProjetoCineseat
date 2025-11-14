package com.maisprati.Cineseat.service;

import com.maisprati.Cineseat.dto.PoltronaAvaliacaoDTO;
import com.maisprati.Cineseat.entities.Poltrona;
import com.maisprati.Cineseat.entities.PoltronaAvaliacao;
import com.maisprati.Cineseat.repositories.PoltronaAvaliacaoRepository;
import com.maisprati.Cineseat.repositories.PoltronaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PoltronaAvaliacaoService {

    @Autowired
    private PoltronaAvaliacaoRepository avaliacaoRepository;

    @Autowired
    private PoltronaRepository poltronaRepository;


    public List<PoltronaAvaliacaoDTO> findAll() {
        return avaliacaoRepository.findAll()
                .stream().map(PoltronaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<PoltronaAvaliacaoDTO> findById(Long id) {
        return avaliacaoRepository.findById(id)
                .map(PoltronaAvaliacaoDTO::new);
    }

    public List<PoltronaAvaliacaoDTO> findByPoltrona(Long poltronaId) {
        return avaliacaoRepository.findByPoltrona_IdPoltrona(poltronaId)
                .stream().map(PoltronaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public List<PoltronaAvaliacaoDTO> findBySala(Long salaId) {
        return avaliacaoRepository
                .findByPoltrona_Sala_IdSala(salaId)
                .stream().map(PoltronaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public List<PoltronaAvaliacaoDTO> findComentariosBySala(Long salaId) {
        return avaliacaoRepository
                .findByPoltrona_Sala_IdSalaAndComentarioIsNotNull(salaId)
                .stream().map(PoltronaAvaliacaoDTO::new)
                .collect(Collectors.toList());
    }

    public Long countBySala(Long salaId) {
        return avaliacaoRepository.countByPoltrona_Sala_IdSala(salaId);
    }

    public Optional<Double> getMediaGeralByPoltrona(Long idPoltrona) {
        return avaliacaoRepository.findMediaGeralByPoltronaId(idPoltrona);
    }

    @Transactional
    public PoltronaAvaliacaoDTO create(PoltronaAvaliacaoDTO dto) {

        Poltrona poltrona = poltronaRepository.findById(dto.getPoltronaId())
                .orElseThrow(() -> new RuntimeException("Poltrona não encontrada"));

        PoltronaAvaliacao entity = new PoltronaAvaliacao();
        entity.setPoltrona(poltrona);
        entity.setIngressoId(dto.getIngressoId());
        entity.setNotaConforto(dto.getNotaConforto());
        entity.setNotaBraco(dto.getNotaBraco());
        entity.setNotaVisao(dto.getNotaVisao());
        entity.setComentario(dto.getComentario());
        entity.setDataAvaliacao(LocalDateTime.now());

        avaliacaoRepository.save(entity);

        atualizarMediaGeralPoltrona(poltrona);

        return new PoltronaAvaliacaoDTO(entity);
    }

    @Transactional
    public Optional<PoltronaAvaliacaoDTO> update(Long id, PoltronaAvaliacaoDTO dto) {

        return avaliacaoRepository.findById(id).map(entity -> {

            if (dto.getNotaConforto() != null) entity.setNotaConforto(dto.getNotaConforto());
            if (dto.getNotaBraco() != null) entity.setNotaBraco(dto.getNotaBraco());
            if (dto.getNotaVisao() != null) entity.setNotaVisao(dto.getNotaVisao());
            if (dto.getComentario() != null) entity.setComentario(dto.getComentario());

            entity.setDataAtualizacao(LocalDateTime.now());

            avaliacaoRepository.save(entity);

            atualizarMediaGeralPoltrona(entity.getPoltrona());

            return new PoltronaAvaliacaoDTO(entity);
        });
    }

    @Transactional
    public boolean delete(Long id) {

        return avaliacaoRepository.findById(id).map(entity -> {

            Poltrona poltrona = entity.getPoltrona();
            avaliacaoRepository.delete(entity);

            atualizarMediaGeralPoltrona(poltrona);

            return true;
        }).orElse(false);
    }

    private void atualizarMediaGeralPoltrona(Poltrona poltrona) {

        Double novaMedia = avaliacaoRepository
                .findMediaGeralByPoltronaId(poltrona.getIdPoltrona())
                .orElse(0.0);

        poltrona.setMediaGeral(novaMedia);
        poltronaRepository.saveAndFlush(poltrona);
    }
}
