package com.maisprati.Cineseat.service;

import com.maisprati.Cineseat.dto.PoltronaDTO;
import com.maisprati.Cineseat.entities.Poltrona;
import com.maisprati.Cineseat.entities.Sala;
import com.maisprati.Cineseat.repositories.PoltronaRepository;
import com.maisprati.Cineseat.repositories.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PoltronaService {

    @Autowired
    private PoltronaRepository poltronaRepository;

    @Autowired
    private SalaRepository salaRepository;

    public List<PoltronaDTO> findAll() {
        List<Poltrona> list = poltronaRepository.findAll();
        return list.stream().map(PoltronaDTO::new).collect(Collectors.toList());
    }

    public PoltronaDTO findById(Long id) {
        Poltrona poltrona = poltronaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Poltrona não encontrada: " + id));
        return new PoltronaDTO(poltrona);
    }

    public List<PoltronaDTO> findBySala(Long salaId) {
        List<Poltrona> list = poltronaRepository.findBySala_IdSala(salaId);
        return list.stream().map(PoltronaDTO::new).collect(Collectors.toList());
    }

    public List<PoltronaDTO> findByTipo(int tipo) {
        List<Poltrona> list = poltronaRepository.findByTipo(tipo);
        return list.stream().map(PoltronaDTO::new).collect(Collectors.toList());
    }

    public Double calcularMediaSala(Long salaId) {
        Double media = poltronaRepository.calcularMediaSala(salaId);
        return media != null ? media : 0.0;
    }

    public List<PoltronaDTO> getRankingBySala(Long salaId) {
        List<Poltrona> list = poltronaRepository.findRankingBySala(salaId);
        return list.stream().map(PoltronaDTO::new).collect(Collectors.toList());
    }

    public PoltronaDTO create(PoltronaDTO dto) {
        Sala sala = salaRepository.findById(dto.getIdSala())
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada: " + dto.getIdSala()));

        Poltrona existente = poltronaRepository.findBySala_IdSalaAndFilaAndColuna(
                dto.getIdSala(),
                dto.getFila(),
                dto.getColuna()
        );

        if (existente != null) {
            throw new IllegalArgumentException(
                    "Já existe uma poltrona na sala " + dto.getIdSala()
                            + " na posição " + dto.getFila() + dto.getColuna()
            );
        }

        Poltrona entity = new Poltrona();
        entity.setFila(dto.getFila());
        entity.setColuna(dto.getColuna());
        entity.setTipo(dto.getTipo());
        entity.setMediaGeral(dto.getMediaGeral());
        entity.setSala(sala);

        entity = poltronaRepository.save(entity);
        return new PoltronaDTO(entity);
    }

    public PoltronaDTO update(Long id, PoltronaDTO dto) {
        Poltrona entity = poltronaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Poltrona não encontrada: " + id));

        entity.setFila(dto.getFila());
        entity.setColuna(dto.getColuna());
        entity.setTipo(dto.getTipo());
        entity.setMediaGeral(dto.getMediaGeral());

        entity = poltronaRepository.save(entity);
        return new PoltronaDTO(entity);
    }

    public void delete(Long id) {
        if (!poltronaRepository.existsById(id)) {
            throw new EntityNotFoundException("Poltrona com id " + id + "não encontrada.");
        }
        poltronaRepository.deleteById(id);
    }
}
