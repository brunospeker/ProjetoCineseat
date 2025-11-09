package com.maisprati.Cineseat.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ingresso_id", unique = true)
    private String ingressoId; // ID do filme na API da Ingresso.com

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String sinopse;

    private String diretor;

    @Column(name = "data_lancamento")
    private LocalDate dataLancamento;

    private Integer duracao; // em minutos

    private String genero;

    private String classificacao;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "trailer_url")
    private String trailerUrl;

    private Double nota;

    @Column(name = "em_cartaz")
    private Boolean emCartaz = true;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "origem")
    private OrigemFilme origem; // LOCAL ou API

    // Enum para identificar a origem do filme
    public enum OrigemFilme {
        LOCAL,  // Criado manualmente no sistema
        API     // Importado da API da Ingresso.com
    }

    // Construtor padrão
    public Filme() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Construtor para filmes da API (consulta externa)
    public Filme(String ingressoId, String titulo) {
        this();
        this.ingressoId = ingressoId;
        this.titulo = titulo;
        this.origem = OrigemFilme.API;
    }

    // Construtor completo para filmes da API
    public static Filme fromAPI(String ingressoId, String titulo, String sinopse, String diretor, LocalDate dataLancamento, Integer duracao, String genero, String classificacao, String posterUrl, String trailerUrl, Double nota) {
        Filme filme = new Filme(ingressoId, titulo);
        filme.setSinopse(sinopse);
        filme.setDiretor(diretor);
        filme.setDataLancamento(dataLancamento);
        filme.setDuracao(duracao);
        filme.setGenero(genero);
        filme.setClassificacao(classificacao);
        filme.setPosterUrl(posterUrl);
        filme.setTrailerUrl(trailerUrl);
        filme.setNota(nota);
        return filme;
    }

    // Construtor para filmes criados localmente
    public static Filme createLocal(String titulo, String sinopse, String diretor) {
        Filme filme = new Filme();
        filme.setTitulo(titulo);
        filme.setSinopse(sinopse);
        filme.setDiretor(diretor);
        filme.setOrigem(OrigemFilme.LOCAL);
        return filme;
    }

    // Método para verificar se é da API
    public boolean isFromAPI() {
        return this.origem == OrigemFilme.API && this.ingressoId != null;
    }

    // Método para verificar se foi criado localmente
    public boolean isLocal() {
        return this.origem == OrigemFilme.LOCAL;
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

    public OrigemFilme getOrigem() {return origem;}

    public void setOrigem(OrigemFilme origem) {this.origem = origem;}

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}