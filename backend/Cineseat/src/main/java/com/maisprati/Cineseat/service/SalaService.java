package com.maisprati.Cineseat.service;

import com.maisprati.Cineseat.dto.SalaDTO;
import com.maisprati.Cineseat.entities.Sala;
import com.maisprati.Cineseat.repositories.SalaRepository;
import com.maisprati.Cineseat.repositories.SalaAvaliacaoRepository;
import com.maisprati.Cineseat.entities.Cinema;
import com.maisprati.Cineseat.repositories.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SalaAvaliacaoRepository salaAvaliacaoRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    // Buscar todas as salas
    public List<SalaDTO> buscarTodasSalas() {
        return salaRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar salas ativas
    public List<SalaDTO> buscarSalasAtivas() {
        return salaRepository.findByAtivaTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar sala por ID
    public Optional<SalaDTO> buscarSalaPorId(Long id) {
        return salaRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Buscar sala por ID da Ingresso.com
    public Optional<SalaDTO> buscarPorIngressoId(String ingressoId) {
        return salaRepository.findByIngressoId(ingressoId)
                .map(this::convertToDTO);
    }

    // Buscar sala por número
    public Optional<SalaDTO> buscarSalaPorNumero(Integer numeroSala) {
        return salaRepository.findByNumeroSala(numeroSala)
                .map(this::convertToDTO);
    }

    // Buscar salas por tipo de tela
    public List<SalaDTO> buscarSalasPorTipoTela(String tipoTela) {
        return salaRepository.findByTipoTela(tipoTela)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar salas por capacidade mínima
    public List<SalaDTO> buscarSalasPorCapacidade(Integer capacidadeMinima) {
        return salaRepository.findByCapacidadeTotalGreaterThanEqual(capacidadeMinima)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar salas acessíveis
    public List<SalaDTO> buscarSalasAcessiveis() {
        return salaRepository.findByAcessivelTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar salas por nome
    public List<SalaDTO> buscarSalasPorNome(String nome) {
        return salaRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //Buscar Salas por Cinema
    public List<SalaDTO> buscarSalasPorCinema(Long idCinema) {
        return salaRepository.findByCinema_IdCinema(idCinema)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Salvar sala
    public SalaDTO salvarSala(SalaDTO salaDTO) {
        if (salaDTO.getIdCinema() == null) {
            throw new RuntimeException("ID do Cinema não pode ser nulo");
        }

        Cinema cinema = cinemaRepository.findById(salaDTO.getIdCinema())
                .orElseThrow(() -> new RuntimeException("Cinema não encontrado"));

        Sala sala = convertToEntity(salaDTO);
        sala.setCinema(cinema);
        cinema.addSala(sala);

        Sala salaSalva = salaRepository.save(sala);
        cinema.atualizarTotalSalas();
        cinemaRepository.save(cinema);

        return convertToDTO(salaSalva);
    }

    // Atualizar sala
    public Optional<SalaDTO> atualizarSala(Long id, SalaDTO salaDTO) {
        return salaRepository.findById(id)
                .map(sala -> {
                    atualizarDadosSala(sala, salaDTO);
                    Sala salaAtualizada = salaRepository.save(sala);
                    return convertToDTO(salaAtualizada);
                });
    }

    // Deletar sala
    public boolean deletarSala(Long id) {
        if (salaRepository.existsById(id)) {
            Sala sala = salaRepository.findById(id).get();
            Cinema cinema = sala.getCinema();
            if (cinema != null) {
                cinema.setSalas(cinema.getSalas().stream()
                    .filter(s -> !s.getIdSala().equals(id))
                    .collect(Collectors.toList()));
                cinema.setTotalSalas(cinema.getSalas().size());
                cinemaRepository.save(cinema);
            }
            salaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Ativar/Desativar sala
    public Optional<SalaDTO> alterarStatusSala(Long id, Boolean ativa) {
        return salaRepository.findById(id)
                .map(sala -> {
                    sala.setAtiva(ativa);
                    Sala salaAtualizada = salaRepository.save(sala);
                    return convertToDTO(salaAtualizada);
                });
    }

    // Converter Entity para DTO
    private SalaDTO convertToDTO(Sala sala) {
        SalaDTO dto = new SalaDTO();
        dto.setIdSala(sala.getIdSala());
        if (sala.getCinema() != null) {
            dto.setIdCinema(sala.getCinema().getIdCinema());
        }
        dto.setIngressoId(sala.getIngressoId());
        dto.setNome(sala.getNome());
        dto.setNumeroSala(sala.getNumeroSala());
        dto.setCapacidadeTotal(sala.getCapacidadeTotal());
        dto.setTipoTela(sala.getTipoTela());
        dto.setTipoSom(sala.getTipoSom());
        dto.setAcessivel(sala.getAcessivel());
        dto.setArCondicionado(sala.getArCondicionado());
        dto.setDescricao(sala.getDescricao());
        dto.setAtiva(sala.getAtiva());

        // Calcular dados de avaliação
        Double mediaAvaliacoes = salaAvaliacaoRepository.calcularMediaAvaliacoesPorSala(sala);
        Long totalAvaliacoes = salaAvaliacaoRepository.contarAvaliacoesPorSala(sala);

        dto.setMediaAvaliacoes(mediaAvaliacoes);
        dto.setTotalAvaliacoes(totalAvaliacoes);

        return dto;
    }

    // Converter DTO para Entity
    private Sala convertToEntity(SalaDTO dto) {
        Sala sala = new Sala();
        sala.setIdSala(dto.getIdSala());
        sala.setIngressoId(dto.getIngressoId());
        sala.setNome(dto.getNome());
        sala.setNumeroSala(dto.getNumeroSala());
        sala.setCapacidadeTotal(dto.getCapacidadeTotal());
        sala.setTipoTela(dto.getTipoTela());
        sala.setTipoSom(dto.getTipoSom());
        sala.setAcessivel(dto.getAcessivel());
        sala.setArCondicionado(dto.getArCondicionado());
        sala.setDescricao(dto.getDescricao());
        sala.setAtiva(dto.getAtiva());
        return sala;
    }

    // Atualizar dados da sala existente
    private void atualizarDadosSala(Sala sala, SalaDTO dto) {
        if (dto.getIngressoId() != null) sala.setIngressoId(dto.getIngressoId());
        if (dto.getNome() != null) sala.setNome(dto.getNome());
        if (dto.getNumeroSala() != null) sala.setNumeroSala(dto.getNumeroSala());
        if (dto.getCapacidadeTotal() != null) sala.setCapacidadeTotal(dto.getCapacidadeTotal());
        if (dto.getTipoTela() != null) sala.setTipoTela(dto.getTipoTela());
        if (dto.getTipoSom() != null) sala.setTipoSom(dto.getTipoSom());
        if (dto.getAcessivel() != null) sala.setAcessivel(dto.getAcessivel());
        if (dto.getArCondicionado() != null) sala.setArCondicionado(dto.getArCondicionado());
        if (dto.getDescricao() != null) sala.setDescricao(dto.getDescricao());
        if (dto.getAtiva() != null) sala.setAtiva(dto.getAtiva());
    }
}