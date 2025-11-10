package com.maisprati.Cineseat.repositories;

import com.maisprati.Cineseat.entities.SalaAvaliacao;
import com.maisprati.Cineseat.entities.Sala;
import com.maisprati.Cineseat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaAvaliacaoRepository extends JpaRepository<SalaAvaliacao, Long> {

    // Buscar avaliações de uma sala específica
    List<SalaAvaliacao> findBySalaAndAtivaTrue(Sala sala);

    // Buscar avaliações de um usuário específico
    List<SalaAvaliacao> findByUsuarioAndAtivaTrue(User usuario);

    // Verificar se usuário já avaliou uma sala
    Optional<SalaAvaliacao> findBySalaAndUsuario(Sala sala, User usuario);

    // Verificar se existe avaliação ativa de um usuário para uma sala
    boolean existsBySalaAndUsuarioAndAtivaTrue(Sala sala, User usuario);

    // Buscar avaliações por nota específica
    List<SalaAvaliacao> findByNotaAndAtivaTrue(Integer nota);

    // Buscar avaliações com comentários
    List<SalaAvaliacao> findBySalaAndComentarioIsNotNullAndAtivaTrue(Sala sala);

    // Calcular média de avaliações gerais de uma sala
    @Query("SELECT AVG(sa.nota) FROM SalaAvaliacao sa WHERE sa.sala = :sala AND sa.ativa = true")
    Double calcularMediaAvaliacoesPorSala(@Param("sala") Sala sala);

    // Calcular média de som de uma sala
    @Query("SELECT AVG(sa.notaSom) FROM SalaAvaliacao sa WHERE sa.sala = :sala AND sa.ativa = true AND sa.notaSom IS NOT NULL")
    Double calcularMediaSomPorSala(@Param("sala") Sala sala);

    // Calcular média de imagem de uma sala
    @Query("SELECT AVG(sa.notaImagem) FROM SalaAvaliacao sa WHERE sa.sala = :sala AND sa.ativa = true AND sa.notaImagem IS NOT NULL")
    Double calcularMediaImagemPorSala(@Param("sala") Sala sala);

    // Calcular média de limpeza de uma sala
    @Query("SELECT AVG(sa.notaLimpeza) FROM SalaAvaliacao sa WHERE sa.sala = :sala AND sa.ativa = true AND sa.notaLimpeza IS NOT NULL")
    Double calcularMediaLimpezaPorSala(@Param("sala") Sala sala);

    // Calcular média de temperatura de uma sala
    @Query("SELECT AVG(sa.notaTemperatura) FROM SalaAvaliacao sa WHERE sa.sala = :sala AND sa.ativa = true AND sa.notaTemperatura IS NOT NULL")
    Double calcularMediaTemperaturaPorSala(@Param("sala") Sala sala);

    // Contar total de avaliações de uma sala
    @Query("SELECT COUNT(sa) FROM SalaAvaliacao sa WHERE sa.sala = :sala AND sa.ativa = true")
    Long contarAvaliacoesPorSala(@Param("sala") Sala sala);

    // Buscar avaliações mais recentes de uma sala
    @Query("SELECT sa FROM SalaAvaliacao sa WHERE sa.sala = :sala AND sa.ativa = true ORDER BY sa.dataCriacao DESC")
    List<SalaAvaliacao> findAvaliacoesRecente(@Param("sala") Sala sala);
}