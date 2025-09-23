package com.maisprati.Cineseat.repositories;

import com.maisprati.Cineseat.entities.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {

    // Buscar salas ativas
    List<Sala> findByAtivaTrue();

    // Buscar sala por número
    Optional<Sala> findByNumeroSala(Integer numeroSala);

    // Buscar salas por tipo de tela
    List<Sala> findByTipoTela(String tipoTela);

    // Buscar salas por tipo de som
    List<Sala> findByTipoSom(String tipoSom);

    // Buscar salas acessíveis
    List<Sala> findByAcessivelTrue();

    // Buscar salas por capacidade mínima
    List<Sala> findByCapacidadeTotalGreaterThanEqual(Integer capacidadeMinima);

    // Buscar salas por nome (busca parcial)
    List<Sala> findByNomeContainingIgnoreCase(String nome);

    // Buscar salas com ar condicionado
    List<Sala> findByArCondicionadoTrue();

    // Buscar salas ordenadas por capacidade
    @Query("SELECT s FROM Sala s WHERE s.ativa = true ORDER BY s.capacidadeTotal DESC")
    List<Sala> findSalasAtivasOrderByCapacidade();

    // Buscar salas disponíveis para um tipo específico
    @Query("SELECT s FROM Sala s WHERE s.ativa = true AND s.tipoTela = :tipoTela AND s.capacidadeTotal >= :capacidadeMinima")
    List<Sala> findSalasDisponiveisPorTipoECapacidade(@Param("tipoTela") String tipoTela,
                                                      @Param("capacidadeMinima") Integer capacidadeMinima);
}