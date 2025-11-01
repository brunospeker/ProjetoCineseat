package com.maisprati.Cineseat.repositories;

import com.maisprati.Cineseat.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
