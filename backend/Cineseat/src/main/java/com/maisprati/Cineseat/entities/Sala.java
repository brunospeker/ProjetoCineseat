package com.maisprati.Cineseat.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "numero_sala")
    private Integer numeroSala;

    @Column(name = "capacidade_total")
    private Integer capacidadeTotal;

    @Column(name = "tipo_tela")
    private String tipoTela; // "2D", "3D", "IMAX", "4DX", etc.

    @Column(name = "tipo_som")
    private String tipoSom; // "Dolby Atmos", "DTS", "Stereo", etc.

    private Boolean acessivel = false; // Para pessoas com deficiência

    @Column(name = "ar_condicionado")
    private Boolean arCondicionado = true;

    private String descricao;

    private Boolean ativa = true;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Relacionamento com avaliações
    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SalaAvaliacao> avaliacoes;

    // Relacionamento com cinema
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    // Construtores
    public Sala() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Sala(String nome, Integer capacidadeTotal) {
        this();
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

    public List<SalaAvaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<SalaAvaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public Cinema getCinema() {return cinema;}
    public void setCinema(Cinema cinema) {this.cinema = cinema;}

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}