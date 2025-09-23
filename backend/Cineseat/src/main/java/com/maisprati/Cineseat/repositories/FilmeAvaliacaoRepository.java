package com.maisprati.Cineseat.repositories;

import com.maisprati.Cineseat.entities.FilmeAvaliacao;
import com.maisprati.Cineseat.entities.Filme;
import com.maisprati.Cineseat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmeAvaliacaoRepository extends JpaRepository<FilmeAvaliacao, Long> {

    // Buscar avaliações de um filme específico
    List<FilmeAvaliacao> findByFilmeAndAtivaTrue(Filme filme);

    // Buscar avaliações de um usuário específico
    List<FilmeAvaliacao> findByUsuarioAndAtivaTrue(User usuario);

    // Verificar se usuário já avaliou um filme
    Optional<FilmeAvaliacao> findByFilmeAndUsuario(Filme filme, User usuario);

    // Verificar se existe avaliação ativa de um usuário para um filme
    boolean existsByFilmeAndUsuarioAndAtivaTrue(Filme filme, User usuario);

    // Buscar avaliações por nota específica
    List<FilmeAvaliacao> findByNotaAndAtivaTrue(Integer nota);

    // Buscar avaliações com comentários
    List<FilmeAvaliacao> findByFilmeAndComentarioIsNotNullAndAtivaTrue(Filme filme);

    // Calcular média de avaliações de um filme
    @Query("SELECT AVG(fa.nota) FROM FilmeAvaliacao fa WHERE fa.filme = :filme AND fa.ativa = true")
    Double calcularMediaAvaliacoesPorFilme(@Param("filme") Filme filme);

    // Contar total de avaliações de um filme
    @Query("SELECT COUNT(fa) FROM FilmeAvaliacao fa WHERE fa.filme = :filme AND fa.ativa = true")
    Long contarAvaliacoesPorFilme(@Param("filme") Filme filme);

    // Buscar avaliações mais recentes de um filme
    @Query("SELECT fa FROM FilmeAvaliacao fa WHERE fa.filme = :filme AND fa.ativa = true ORDER BY fa.dataCriacao DESC")
    List<FilmeAvaliacao> findAvaliacoesRecentesPorFilme(@Param("filme") Filme filme);

    // Buscar avaliações mais curtidas de um filme
    @Query("SELECT fa FROM FilmeAvaliacao fa WHERE fa.filme = :filme AND fa.ativa = true ORDER BY fa.likesCount DESC")
    List<FilmeAvaliacao> findAvaliacoesMaisCurtidasPorFilme(@Param("filme") Filme filme);

    // Buscar estatísticas de notas de um filme
    @Query("SELECT fa.nota, COUNT(fa) FROM FilmeAvaliacao fa WHERE fa.filme = :filme AND fa.ativa = true GROUP BY fa.nota ORDER BY fa.nota")
    List<Object[]> obterEstatisticasNotasPorFilme(@Param("filme") Filme filme);
}