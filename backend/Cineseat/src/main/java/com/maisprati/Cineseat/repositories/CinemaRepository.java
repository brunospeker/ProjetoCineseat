package com.maisprati.Cineseat.repositories;

import com.maisprati.Cineseat.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    List<Cinema> findByCidadeContainingIgnoreCase(String cidade);
    List<Cinema> findByEstadoContainingIgnoreCase(String estado);
    List<Cinema> findByBairroContainingIgnoreCase(String bairro);

    List<Cinema> findAllByAtivoTrue();
    List<Cinema> findAllByTemBomboniereTrue();

    List<Cinema> findByTotalSalas(Integer totalSalas);
    List<Cinema> findByTotalSalasBetween(Integer min, Integer max);

    List<Cinema> findByMediaGeral(Double mediaGeral);

    @Query("SELECT c FROM Cinema c ORDER BY COALESCE(c.mediaGeral, 0) DESC")
    List<Cinema> rankingCinemas();

    @Query("SELECT c FROM Cinema c WHERE LOWER(c.cidade) LIKE LOWER(CONCAT('%', :cidade, '%')) ORDER BY COALESCE(c.mediaGeral,0) DESC")
    List<Cinema> rankingCinemasByCity(@Param("cidade") String cidade);

    @Query("SELECT c FROM Cinema c WHERE LOWER(c.estado) LIKE LOWER(CONCAT('%', :estado, '%')) ORDER BY COALESCE(c.mediaGeral,0) DESC")
    List<Cinema> rankingCinemasByState(@Param("estado") String estado);
}
