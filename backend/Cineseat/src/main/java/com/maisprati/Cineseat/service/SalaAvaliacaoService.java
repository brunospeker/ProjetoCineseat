package com.maisprati.Cineseat.service;

import com.maisprati.Cineseat.dto.SalaAvaliacaoDTO;
import com.maisprati.Cineseat.entities.SalaAvaliacao;
import com.maisprati.Cineseat.entities.Sala;
import com.maisprati.Cineseat.entities.User;
import com.maisprati.Cineseat.repositories.SalaAvaliacaoRepository;
import com.maisprati.Cineseat.repositories.SalaRepository;
import com.maisprati.Cineseat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaAvaliacaoService {

    @Autowired
    private SalaAvaliacaoRepository salaAvaliacaoRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private UserRepository userRepository;

    // Buscar todas as avaliações
    public List<SalaAvaliacaoDTO> buscarTodasAvaliacoes() {
        return salaAvaliacaoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar avaliações por sala
    public List<SalaAvaliacaoDTO> buscarAvaliacoesPorSala(Long salaId) {
        Optional<Sala> sala = salaRepository.findById(salaId);
        if (sala.isPresent()) {
            return salaAvaliacaoRepository.findBySalaAndAtivaTrue(sala.get())
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    // Buscar avaliações por usuário
    public List<SalaAvaliacaoDTO> buscarAvaliacoesPorUsuario(Long usuarioId) {
        Optional<User> usuario = userRepository.findById(usuarioId);
        if (usuario.isPresent()) {
            return salaAvaliacaoRepository.findByUsuarioAndAtivaTrue(usuario.get())
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    // Buscar avaliação específica
    public Optional<SalaAvaliacaoDTO> buscarAvaliacaoPorId(Long id) {
        return salaAvaliacaoRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Verificar se usuário já avaliou sala
    public boolean usuarioJaAvaliouSala(Long salaId, Long usuarioId) {
        Optional<Sala> sala = salaRepository.findById(salaId);
        Optional<User> usuario = userRepository.findById(usuarioId);

        if (sala.isPresent() && usuario.isPresent()) {
            return salaAvaliacaoRepository.existsBySalaAndUsuarioAndAtivaTrue(sala.get(), usuario.get());
        }
        return false;
    }

    // Criar nova avaliação
    public Optional<SalaAvaliacaoDTO> criarAvaliacao(SalaAvaliacaoDTO avaliacaoDTO) {
        // Verificar se sala e usuário existem
        Optional<Sala> sala = salaRepository.findById(avaliacaoDTO.getSalaId());
        Optional<User> usuario = userRepository.findById(avaliacaoDTO.getUsuarioId());

        if (sala.isEmpty() || usuario.isEmpty()) {
            return Optional.empty();
        }

        // Verificar se usuário já avaliou esta sala
        if (usuarioJaAvaliouSala(avaliacaoDTO.getSalaId(), avaliacaoDTO.getUsuarioId())) {
            return Optional.empty();
        }

        SalaAvaliacao avaliacao = new SalaAvaliacao();
        avaliacao.setSala(sala.get());
        avaliacao.setUsuario(usuario.get());
        avaliacao.setNota(avaliacaoDTO.getNota());
        avaliacao.setNotaSom(avaliacaoDTO.getNotaSom());
        avaliacao.setNotaImagem(avaliacaoDTO.getNotaImagem());
        avaliacao.setNotaLimpeza(avaliacaoDTO.getNotaLimpeza());
        avaliacao.setNotaTemperatura(avaliacaoDTO.getNotaTemperatura());
        avaliacao.setComentario(avaliacaoDTO.getComentario());

        SalaAvaliacao avaliacaoSalva = salaAvaliacaoRepository.save(avaliacao);
        return Optional.of(convertToDTO(avaliacaoSalva));
    }

    // Atualizar avaliação
    public Optional<SalaAvaliacaoDTO> atualizarAvaliacao(Long id, SalaAvaliacaoDTO avaliacaoDTO) {
        return salaAvaliacaoRepository.findById(id)
                .map(avaliacao -> {
                    if (avaliacaoDTO.getNota() != null) avaliacao.setNota(avaliacaoDTO.getNota());
                    if (avaliacaoDTO.getNotaSom() != null) avaliacao.setNotaSom(avaliacaoDTO.getNotaSom());
                    if (avaliacaoDTO.getNotaImagem() != null) avaliacao.setNotaImagem(avaliacaoDTO.getNotaImagem());
                    if (avaliacaoDTO.getNotaLimpeza() != null) avaliacao.setNotaLimpeza(avaliacaoDTO.getNotaLimpeza());
                    if (avaliacaoDTO.getNotaTemperatura() != null) avaliacao.setNotaTemperatura(avaliacaoDTO.getNotaTemperatura());
                    if (avaliacaoDTO.getComentario() != null) avaliacao.setComentario(avaliacaoDTO.getComentario());

                    SalaAvaliacao avaliacaoAtualizada = salaAvaliacaoRepository.save(avaliacao);
                    return convertToDTO(avaliacaoAtualizada);
                });
    }

    // Deletar avaliação (soft delete)
    public boolean deletarAvaliacao(Long id) {
        return salaAvaliacaoRepository.findById(id)
                .map(avaliacao -> {
                    avaliacao.setAtiva(false);
                    salaAvaliacaoRepository.save(avaliacao);
                    return true;
                }).orElse(false);
    }

    // Curtir avaliação
    public Optional<SalaAvaliacaoDTO> curtirAvaliacao(Long id) {
        return salaAvaliacaoRepository.findById(id)
                .map(avaliacao -> {
                    avaliacao.incrementarLikes();
                    SalaAvaliacao avaliacaoAtualizada = salaAvaliacaoRepository.save(avaliacao);
                    return convertToDTO(avaliacaoAtualizada);
                });
    }

    // Descurtir avaliação
    public Optional<SalaAvaliacaoDTO> descurtirAvaliacao(Long id) {
        return salaAvaliacaoRepository.findById(id)
                .map(avaliacao -> {
                    avaliacao.incrementarDislikes();
                    SalaAvaliacao avaliacaoAtualizada = salaAvaliacaoRepository.save(avaliacao);
                    return convertToDTO(avaliacaoAtualizada);
                });
    }

    // Obter estatísticas detalhadas de uma sala
    public Map<String, Object> obterEstatisticasSala(Long salaId) {
        Optional<Sala> sala = salaRepository.findById(salaId);
        if (sala.isEmpty()) {
            return Map.of();
        }

        Sala salaEntity = sala.get();
        Double mediaGeral = salaAvaliacaoRepository.calcularMediaAvaliacoesPorSala(salaEntity);
        Double mediaSom = salaAvaliacaoRepository.calcularMediaSomPorSala(salaEntity);
        Double mediaImagem = salaAvaliacaoRepository.calcularMediaImagemPorSala(salaEntity);
        Double mediaLimpeza = salaAvaliacaoRepository.calcularMediaLimpezaPorSala(salaEntity);
        Double mediaTemperatura = salaAvaliacaoRepository.calcularMediaTemperaturaPorSala(salaEntity);
        Long total = salaAvaliacaoRepository.contarAvaliacoesPorSala(salaEntity);

        return Map.of(
                "media_geral", mediaGeral != null ? mediaGeral : 0.0,
                "media_som", mediaSom != null ? mediaSom : 0.0,
                "media_imagem", mediaImagem != null ? mediaImagem : 0.0,
                "media_limpeza", mediaLimpeza != null ? mediaLimpeza : 0.0,
                "media_temperatura", mediaTemperatura != null ? mediaTemperatura : 0.0,
                "total_avaliacoes", total
        );
    }

    // Converter Entity para DTO
    private SalaAvaliacaoDTO convertToDTO(SalaAvaliacao avaliacao) {
        SalaAvaliacaoDTO dto = new SalaAvaliacaoDTO();
        dto.setId(avaliacao.getIdSalaAvaliacao());
        dto.setSalaId(avaliacao.getSala().getIdSala());
        dto.setSalaNome(avaliacao.getSala().getNome());
        dto.setUsuarioId(avaliacao.getUsuario().getId());
        dto.setUsuarioNome(avaliacao.getUsuario().getUsername());
        dto.setNota(avaliacao.getNota());
        dto.setNotaSom(avaliacao.getNotaSom());
        dto.setNotaImagem(avaliacao.getNotaImagem());
        dto.setNotaLimpeza(avaliacao.getNotaLimpeza());
        dto.setNotaTemperatura(avaliacao.getNotaTemperatura());
        dto.setComentario(avaliacao.getComentario());
        dto.setDataCriacao(avaliacao.getDataCriacao());
        dto.setDataAtualizacao(avaliacao.getDataAtualizacao());
        dto.setAtiva(avaliacao.getAtiva());
        dto.setLikesCount(avaliacao.getLikesCount());
        dto.setDislikesCount(avaliacao.getDislikesCount());

        // Calcular nota média das categorias
        Double notaMedia = avaliacao.calcularNotaMedia();
        dto.setNotaMediaCategorias(notaMedia);

        return dto;
    }
}