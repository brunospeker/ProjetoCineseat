package com.maisprati.Cineseat.repositories;

import com.maisprati.Cineseat.entities.Poltrona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoltronaRepository extends JpaRepository<Poltrona, Long> {
    List<Poltrona> findBySala_IdSala(Long salaId);

    List<Poltrona> findByTipo(int tipo);

    Poltrona findBySala_IdSalaAndFilaAndColuna(Long salaId, String fila, String coluna);

    @Query("SELECT AVG(p.mediaGeral) FROM Poltrona p WHERE p.sala.idSala = :salaId")
    Double calcularMediaSala(Long salaId);

    @Query("SELECT p FROM Poltrona p WHERE p.sala.idSala = :salaId ORDER BY p.mediaGeral DESC NULLS LAST")
    List<Poltrona> findRankingBySala(@Param("salaId") Long salaId);
}
