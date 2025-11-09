package com.maisprati.Cineseat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class FilmeDTO {

    private Long id;

    @JsonProperty("ingresso_id")
    private String ingressoId;

    private String titulo;

    private String sinopse;

    private String diretor;

    @JsonProperty("data_lancamento")
    private LocalDate dataLancamento;

    private Integer duracao;

    private String genero;

    private String classificacao;

    @JsonProperty("poster_url")
    private String posterUrl;

    @JsonProperty("trailer_url")
    private String trailerUrl;

    private Double nota;

    @JsonProperty("em_cartaz")
    private Boolean emCartaz;

    private String origem; // "LOCAL" ou "API"

    // Construtores
    public FilmeDTO() {}

    public FilmeDTO(String titulo, String sinopse) {
        this.titulo = titulo;
        this.sinopse = sinopse;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngressoId() {
        return ingressoId;
    }

    public void setIngressoId(String ingressoId) {
        this.ingressoId = ingressoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Boolean getEmCartaz() {
        return emCartaz;
    }

    public void setEmCartaz(Boolean emCartaz) {
        this.emCartaz = emCartaz;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }
}
