package com.maisprati.Cineseat.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maisprati.Cineseat.dto.CinemaAvaliacaoDTO;
import com.maisprati.Cineseat.entities.Cinema;
import com.maisprati.Cineseat.entities.CinemaAvaliacao;
import com.maisprati.Cineseat.entities.User;
import com.maisprati.Cineseat.service.CinemaAvaliacaoService;

@RestController
@RequestMapping("/api/cinema-avaliacoes")
public class CinemaAvaliacaoController {

    @Autowired
    private CinemaAvaliacaoService avaliacaoService;

    @GetMapping
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAllAvaliacoes() {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findAll();
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CinemaAvaliacaoDTO> getAvaliacaoById(@PathVariable("id") Long idAvaliacaoCinema) {
        Optional<CinemaAvaliacaoDTO> avaliacao = avaliacaoService.findById(idAvaliacaoCinema);
        return avaliacao.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cinema/{cinemaId}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesByCinema(@PathVariable("cinemaId") Long idCinema) {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findByCinemaId(idCinema);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesByNotaGeral(@PathVariable("usuarioId") Long idUsuario) {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findByUsuarioId(idUsuario);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/notaGeral/{notaGeral}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesByUsuario(@PathVariable("notaGeral") Integer notaGeral) {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findByNotaGeral(notaGeral);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/notaLimpeza/{notaLimpeza}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesByNotaLimpeza(@PathVariable("notaLimpeza") Integer notaLimpeza) {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findByNotaLimpeza(notaLimpeza);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/notaPreco/{notaPreco}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesByNotaPreco(@PathVariable("notaPreco") Integer notaPreco) {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findByNotaPreco(notaPreco);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/notaAlimentacao/{notaAlimentacao}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesByNotaAlimentacao(@PathVariable("notaAlimentacao") Integer notaAlimentacao) {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findByNotaAlimentacao(notaAlimentacao);
        return ResponseEntity.ok(avaliacoes);
    }
    
    @GetMapping("/notaAtendimento/{notaAtendimento}")
    public ResponseEntity<List<CinemaAvaliacaoDTO>> getAvaliacoesByNotAtendimento(@PathVariable("notaAtendimento") Integer notaAtendimento) {
        List<CinemaAvaliacaoDTO> avaliacoes = avaliacaoService.findByNotaAtendimento(notaAtendimento);
        return ResponseEntity.ok(avaliacoes);
    }

    @PostMapping
    public ResponseEntity<CinemaAvaliacaoDTO> createAvaliacaoCinema(@RequestBody CinemaAvaliacaoDTO avaliacaoDTO) {
        CinemaAvaliacao avaliacao = new CinemaAvaliacao();

        Cinema cinema = new Cinema();
        cinema.setIdCinema(avaliacaoDTO.getIdCinema());
        avaliacao.setCinema(cinema);

        User usuario = new User();
        usuario.setId(avaliacaoDTO.getIdUsuario());
        avaliacao.setUsuario(usuario);

        if (avaliacaoDTO.getNotaGeral() != null) {
            avaliacao.setNotaGeral(avaliacaoDTO.getNotaGeral());
        }
        if (avaliacaoDTO.getNotaLimpeza() != null) {
            avaliacao.setNotaLimpeza(avaliacaoDTO.getNotaLimpeza());
        }
        if (avaliacaoDTO.getNotaAtendimento() != null) {
            avaliacao.setNotaAtendimento(avaliacaoDTO.getNotaAtendimento());
        }
        if (avaliacaoDTO.getNotaPreco() != null) {
            avaliacao.setNotaPreco(avaliacaoDTO.getNotaPreco());
        }
        if (avaliacaoDTO.getNotaAlimentacao() != null) {
            avaliacao.setNotaAlimentacao(avaliacaoDTO.getNotaAlimentacao());
        }
        if (avaliacaoDTO.getComentario() != null) {
            avaliacao.setComentario(avaliacaoDTO.getComentario());
        }

        CinemaAvaliacaoDTO dto = avaliacaoService.createAvaliacaoCinema(avaliacao);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CinemaAvaliacaoDTO> updateAvaliacao(
            @PathVariable("id") Long idAvaliacaoCinema,
            @RequestBody CinemaAvaliacaoDTO dto) {
        Optional<CinemaAvaliacaoDTO> updated = avaliacaoService.updateAvaliacao(idAvaliacaoCinema, dto);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable("id") Long idAvaliacaoCinema) {
        boolean deleted = avaliacaoService.deleteAvaliacao(idAvaliacaoCinema);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
