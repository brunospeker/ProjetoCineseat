package com.maisprati.Cineseat.repositories;

import com.maisprati.Cineseat.entities.PoltronaAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PoltronaAvaliacaoRepository extends JpaRepository<PoltronaAvaliacao, Long> {
    List<PoltronaAvaliacao> findByPoltrona_IdPoltrona(Long poltronaId);
    List<PoltronaAvaliacao> findByPoltrona_Sala_IdSala(Long salaId);

    List<PoltronaAvaliacao> findByPoltrona_Sala_IdSalaAndComentarioIsNotNull(Long salaId);

    Long countByPoltrona_Sala_IdSala(Long salaId);

    @Query("SELECT AVG(a.notaGeral) FROM PoltronaAvaliacao a WHERE a.poltrona.id = :idPoltrona")
    Optional<Double> findMediaGeralByPoltronaId(@Param("idPoltrona") Long idPoltrona);
}
