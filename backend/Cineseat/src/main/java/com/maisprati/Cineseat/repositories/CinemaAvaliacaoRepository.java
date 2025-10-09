package com.maisprati.Cineseat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maisprati.Cineseat.entities.CinemaAvaliacao;

@Repository
public interface CinemaAvaliacaoRepository extends JpaRepository<CinemaAvaliacao, Long> {
    List<CinemaAvaliacao> findByCinemaIdCinema(Long idCinema);
    List<CinemaAvaliacao> findByUsuarioId(Long idUsuario);
    List<CinemaAvaliacao> findByNotaGeral(Integer notaGeral);
    List<CinemaAvaliacao> findByNotaLimpeza(Integer notaLimpeza);
    List<CinemaAvaliacao> findByNotaPreco(Integer notaPreco);
    List<CinemaAvaliacao> findByNotaAlimentacao(Integer notaAlimentacao);
    List<CinemaAvaliacao> findByNotaAtendimento(Integer notaAtendimento);

}
