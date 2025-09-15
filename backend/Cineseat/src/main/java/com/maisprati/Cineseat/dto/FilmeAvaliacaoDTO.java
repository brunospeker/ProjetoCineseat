package com.maisprati.cineseat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class FilmeAvaliacaoDTO {

    private Long id;

    @JsonProperty("filme_id")
    private Long filmeId;

    @JsonProperty("filme_titulo")
    private String filmeTitulo;

    @JsonProperty("usuario_id")
    private Long usuarioId;

    @JsonProperty("usuario_nome")
    private String usuarioNome;

    private Integer nota;

    private String comentario;

    @JsonProperty("data_criacao")
    private LocalDateTime dataCriacao;

    @JsonProperty("data_atualizacao")
    private LocalDateTime dataAtualizacao;

    private Boolean ativa;

    @JsonProperty("likes_count")
    private Integer likesCount;

    @JsonProperty("dislikes_count")
    private Integer dislikesCount;

    // Construtores
    public FilmeAvaliacaoDTO() {}

    public FilmeAvaliacaoDTO(Long filmeId, Long usuarioId, Integer nota) {
        this.filmeId = filmeId;
        this.usuarioId = usuarioId;
        this.nota = nota;
    }

    public FilmeAvaliacaoDTO(Long filmeId, Long usuarioId, Integer nota, String comentario) {
        this(filmeId, usuarioId, nota);
        this.comentario = comentario;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFilmeId() {
        return filmeId;
    }

    public void setFilmeId(Long filmeId) {
        this.filmeId = filmeId;
    }

    public String getFilmeTitulo() {
        return filmeTitulo;
    }

    public void setFilmeTitulo(String filmeTitulo) {
        this.filmeTitulo = filmeTitulo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
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
}