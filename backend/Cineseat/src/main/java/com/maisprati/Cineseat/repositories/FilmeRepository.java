package com.maisprati.Cineseat.repositories;

import com.maisprati.Cineseat.entities.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    // Buscar filme pelo ID da API da Ingresso.com
    Optional<Filme> findByIngressoId(String ingressoId);

    // Verificar se já existe um filme com esse ID da Ingresso.com
    boolean existsByIngressoId(String ingressoId);

    // Buscar filmes em cartaz
    List<Filme> findByEmCartazTrue();

    // Buscar filmes por gênero
    List<Filme> findByGeneroContainingIgnoreCase(String genero);

    // Buscar filmes por título (busca parcial, case-insensitive)
    List<Filme> findByTituloContainingIgnoreCase(String titulo);

    // Buscar filmes por diretor
    List<Filme> findByDiretorContainingIgnoreCase(String diretor);

    // Buscar filmes por classificação
    List<Filme> findByClassificacao(String classificacao);

    // Buscar filmes com nota maior que um valor específico
    @Query("SELECT f FROM Filme f WHERE f.nota >= :notaMinima ORDER BY f.nota DESC")
    List<Filme> findByNotaGreaterThanEqualOrderByNotaDesc(@Param("notaMinima") Double notaMinima);

    // Buscar filmes em cartaz ordenados por data de lançamento (mais recentes primeiro)
    @Query("SELECT f FROM Filme f WHERE f.emCartaz = true ORDER BY f.dataLancamento DESC")
    List<Filme> findFilmesEmCartazOrderByDataLancamento();
}
