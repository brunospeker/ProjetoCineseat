package com.maisprati.Cineseat.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.maisprati.Cineseat.dto.CinemaAvaliacaoDTO;
import com.maisprati.Cineseat.entities.CinemaAvaliacao;

@Repository
public interface CinemaAvaliacaoRepository extends JpaRepository<CinemaAvaliacao, Long> {
    List<CinemaAvaliacao> findByCinema_IdCinema(Long idCinema);
    List<CinemaAvaliacao> findByUsuario_Id(Long idUsuario);
    List<CinemaAvaliacao> findByUsuario_IdAndCinema_IdCinema(Long idUsuario, Long idCinema);

    List<CinemaAvaliacao> findByCinema_IdCinemaAndComentarioIsNotNull(Long idCinema);

    List<CinemaAvaliacao> findByNotaGeral(Integer notaGeral);
    List<CinemaAvaliacao> findByNotaLimpeza(Integer notaLimpeza);
    List<CinemaAvaliacao> findByNotaPreco(Integer notaPreco);
    List<CinemaAvaliacao> findByNotaAlimentacao(Integer notaAlimentacao);
    List<CinemaAvaliacao> findByNotaAtendimento(Integer notaAtendimento);

    Long countByCinema_IdCinema(Long idCinema);

    Long countByUsuario_Id(Long idUsuario);

    @Query("""
           SELECT a 
           FROM CinemaAvaliacao a 
           WHERE a.cinema.idCinema = :idCinema 
           ORDER BY a.dataAtualizacao DESC
           """)
    List<CinemaAvaliacao> findAvaliacoesRecentes(@Param("idCinema") Long idCinema);

    @Query("""
    SELECT new com.maisprati.Cineseat.dto.CinemaAvaliacaoDTO(
        a.cinema.idCinema,
        AVG(a.notaGeral),
        AVG(a.notaLimpeza),
        AVG(a.notaPreco),
        AVG(a.notaAlimentacao),
        AVG(a.notaAtendimento)
    )
    FROM CinemaAvaliacao a
    WHERE a.cinema.idCinema = :idCinema 
    GROUP BY a.cinema.idCinema
    """)
    Optional<CinemaAvaliacaoDTO> findAverageRatingsByCinemaId(@Param("idCinema") Long idCinema);

    @Query("""
    SELECT new com.maisprati.Cineseat.dto.CinemaAvaliacaoDTO(
        a.cinema.idCinema,
        AVG(a.notaGeral),
        AVG(a.notaLimpeza),
        AVG(a.notaPreco),
        AVG(a.notaAlimentacao),
        AVG(a.notaAtendimento)
    )
    FROM CinemaAvaliacao a
    GROUP BY a.cinema.idCinema
    ORDER BY AVG(a.notaGeral) DESC
    """)
    List<CinemaAvaliacaoDTO> rankingTopCinemasByAverageScore();

    @Query("""
    SELECT new com.maisprati.Cineseat.dto.CinemaAvaliacaoDTO(
        a.cinema.idCinema,
        AVG(a.notaGeral),
        AVG(a.notaLimpeza),
        AVG(a.notaPreco),
        AVG(a.notaAlimentacao),
        AVG(a.notaAtendimento)
    )
    FROM CinemaAvaliacao a
    WHERE a.cinema.cidade = :cidade
    GROUP BY a.cinema.idCinema
    ORDER BY AVG(a.notaGeral) DESC
    """)
    List<CinemaAvaliacaoDTO> rankingTopCinemasByCity(@Param("cidade") String cidade);

    @Query("""
    SELECT new com.maisprati.Cineseat.dto.CinemaAvaliacaoDTO(
        a.cinema.idCinema,
        AVG(a.notaGeral),
        AVG(a.notaLimpeza),
        AVG(a.notaPreco),
        AVG(a.notaAlimentacao),
        AVG(a.notaAtendimento)
    )
    FROM CinemaAvaliacao a
    WHERE a.cinema.estado = :estado
    GROUP BY a.cinema.idCinema
    ORDER BY AVG(a.notaGeral) DESC
    """)
    List<CinemaAvaliacaoDTO> rankingTopCinemasByState(@Param("estado") String estado);
}