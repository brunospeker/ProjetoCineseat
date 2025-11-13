package com.maisprati.Cineseat.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.maisprati.Cineseat.entities.CinemaAvaliacao;

@Repository
public interface CinemaAvaliacaoRepository extends JpaRepository<CinemaAvaliacao, Long> {
    List<CinemaAvaliacao> findByCinema_IdCinema(Long idCinema);
    List<CinemaAvaliacao> findByUsuario_Id(Long idUsuario);
    List<CinemaAvaliacao> findByUsuario_IdAndCinema_IdCinema(Long idUsuario, Long idCinema);

    List<CinemaAvaliacao> findByCinema_IdCinemaAndComentarioIsNotNull(Long idCinema);

    Long countByCinema_IdCinema(Long idCinema);

    Long countByUsuario_Id(Long idUsuario);

    @Query("SELECT AVG(a.notaGeral) FROM CinemaAvaliacao a WHERE a.cinema.idCinema = :idCinema AND a.notaGeral IS NOT NULL AND a.notaGeral > 0")
    Optional<Double> findMediaGeralByCinemaId(@Param("idCinema") Long idCinema);
}