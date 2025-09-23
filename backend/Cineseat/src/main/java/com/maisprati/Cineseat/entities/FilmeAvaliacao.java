package com.maisprati.Cineseat.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "filme_avaliacoes")
public class FilmeAvaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @Column(nullable = false)
    private Integer nota; // 1 a 5 estrelas

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    private Boolean ativa = true;

    // Para controlar se a avaliação foi útil para outros usuários
    @Column(name = "likes_count")
    private Integer likesCount = 0;

    @Column(name = "dislikes_count")
    private Integer dislikesCount = 0;

    // Construtores
    public FilmeAvaliacao() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public FilmeAvaliacao(Filme filme, User usuario, Integer nota) {
        this();
        this.filme = filme;
        this.usuario = usuario;
        this.nota = nota;
    }

    public FilmeAvaliacao(Filme filme, User usuario, Integer nota, String comentario) {
        this(filme, usuario, nota);
        this.comentario = comentario;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("Nota deve estar entre 1 e 5");
        }
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(Integer dislikesCount) {
        this.dislikesCount = dislikesCount;
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Método para incrementar likes
    public void incrementarLikes() {
        this.likesCount = (this.likesCount == null) ? 1 : this.likesCount + 1;
    }

    // Método para incrementar dislikes
    public void incrementarDislikes() {
        this.dislikesCount = (this.dislikesCount == null) ? 1 : this.dislikesCount + 1;
    }
}