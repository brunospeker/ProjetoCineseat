package com.maisprati.Cineseat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SalaDTO {

    private Long id;

    private String nome;

    @JsonProperty("numero_sala")
    private Integer numeroSala;

    @JsonProperty("capacidade_total")
    private Integer capacidadeTotal;

    @JsonProperty("tipo_tela")
    private String tipoTela;

    @JsonProperty("tipo_som")
    private String tipoSom;

    private Boolean acessivel;

    @JsonProperty("ar_condicionado")
    private Boolean arCondicionado;

    private String descricao;

    private Boolean ativa;

    // Campos calculados (não persistidos)
    @JsonProperty("media_avaliacoes")
    private Double mediaAvaliacoes;

    @JsonProperty("total_avaliacoes")
    private Long totalAvaliacoes;

    // Construtores
    public SalaDTO() {}

    public SalaDTO(String nome, Integer capacidadeTotal) {
        this.nome = nome;
        this.capacidadeTotal = capacidadeTotal;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumeroSala() {
        return numeroSala;
    }

    public void setNumeroSala(Integer numeroSala) {
        this.numeroSala = numeroSala;
    }

    public Integer getCapacidadeTotal() {
        return capacidadeTotal;
    }

    public void setCapacidadeTotal(Integer capacidadeTotal) {
        this.capacidadeTotal = capacidadeTotal;
    }

    public String getTipoTela() {
        return tipoTela;
    }

    public void setTipoTela(String tipoTela) {
        this.tipoTela = tipoTela;
    }

    public String getTipoSom() {
        return tipoSom;
    }

    public void setTipoSom(String tipoSom) {
        this.tipoSom = tipoSom;
    }

    public Boolean getAcessivel() {
        return acessivel;
    }

    public void setAcessivel(Boolean acessivel) {
        this.acessivel = acessivel;
    }

    public Boolean getArCondicionado() {
        return arCondicionado;
    }

    public void setArCondicionado(Boolean arCondicionado) {
        this.arCondicionado = arCondicionado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public Double getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(Double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    public Long getTotalAvaliacoes() {
        return totalAvaliacoes;
    }

    public void setTotalAvaliacoes(Long totalAvaliacoes) {
        this.totalAvaliacoes = totalAvaliacoes;
    }
}