package com.maisprati.Cineseat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class SalaAvaliacaoDTO {

    private Long id;

    @JsonProperty("sala_id")
    private Long salaId;

    @JsonProperty("sala_nome")
    private String salaNome;

    @JsonProperty("usuario_id")
    private Long usuarioId;

    @JsonProperty("usuario_nome")
    private String usuarioNome;

    private Integer nota;

    @JsonProperty("nota_conforto")
    private Integer notaConforto;

    @JsonProperty("nota_som")
    private Integer notaSom;

    @JsonProperty("nota_imagem")
    private Integer notaImagem;

    @JsonProperty("nota_limpeza")
    private Integer notaLimpeza;

    @JsonProperty("nota_temperatura")
    private Integer notaTemperatura;

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

    // Campo calculado
    @JsonProperty("nota_media_categorias")
    private Double notaMediaCategorias;

    // Construtores
    public SalaAvaliacaoDTO() {}

    public SalaAvaliacaoDTO(Long salaId, Long usuarioId, Integer nota) {
        this.salaId = salaId;
        this.usuarioId = usuarioId;
        this.nota = nota;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSalaId() {
        return salaId;
    }

    public void setSalaId(Long salaId) {
        this.salaId = salaId;
    }

    public String getSalaNome() {
        return salaNome;
    }

    public void setSalaNome(String salaNome) {
        this.salaNome = salaNome;
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

    public Integer getNotaConforto() {
        return notaConforto;
    }

    public void setNotaConforto(Integer notaConforto) {
        this.notaConforto = notaConforto;
    }

    public Integer getNotaSom() {
        return notaSom;
    }

    public void setNotaSom(Integer notaSom) {
        this.notaSom = notaSom;
    }

    public Integer getNotaImagem() {
        return notaImagem;
    }

    public void setNotaImagem(Integer notaImagem) {
        this.notaImagem = notaImagem;
    }

    public Integer getNotaLimpeza() {
        return notaLimpeza;
    }

    public void setNotaLimpeza(Integer notaLimpeza) {
        this.notaLimpeza = notaLimpeza;
    }

    public Integer getNotaTemperatura() {
        return notaTemperatura;
    }

    public void setNotaTemperatura(Integer notaTemperatura) {
        this.notaTemperatura = notaTemperatura;
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

    public Double getNotaMediaCategorias() {
        return notaMediaCategorias;
    }

    public void setNotaMediaCategorias(Double notaMediaCategorias) {
        this.notaMediaCategorias = notaMediaCategorias;
    }
}