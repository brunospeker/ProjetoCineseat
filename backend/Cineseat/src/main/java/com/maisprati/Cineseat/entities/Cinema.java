package com.maisprati.Cineseat.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cinemas")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCinema;

    @Column(nullable = false)
    private String nomeCinema;

    private String site;
    private String cnpj;

    @Column(nullable = false)
    private String estado;
    private String uf;

    @Column(nullable = false)
    private String cidade;
    private String idCidade;

    private String bairro;
    private String numero;
    private String imagensJson;
    private Boolean temBomboniere;

    @Column(name = "total_salas")
    private Integer totalSalas;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @Column(nullable = false)
    private Boolean ativo;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sala> salas;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CinemaAvaliacao> avaliacoes;

    public Cinema() {}

    public Cinema(Long idCinema, String nomeCinema, String site, String cnpj, String estado, String uf,
                  String cidade, String idCidade, String bairro, String numero,
                  String imagensJson, Boolean temBomboniere, Integer totalSalas, Boolean ativo) {
        this.idCinema = idCinema;
        this.nomeCinema = nomeCinema;
        this.site = site;
        this.cnpj = cnpj;
        this.estado = estado;
        this.uf = uf;
        this.cidade = cidade;
        this.idCidade = idCidade;
        this.bairro = bairro;
        this.numero = numero;
        this.imagensJson = imagensJson;
        this.temBomboniere = temBomboniere;
        this.totalSalas = totalSalas;
        this.ativo = ativo;
    }

    @PrePersist
    public void inicializarDatas() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        if (this.ativo == null) {
            this.ativo = true;
        }
    }

    @PreUpdate
    public void atualizar() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Long getIdCinema() { return idCinema; }
    public void setIdCinema(Long idCinema) { this.idCinema = idCinema; }

    public String getNomeCinema() { return nomeCinema; }
    public void setNomeCinema(String nomeCinema) { this.nomeCinema = nomeCinema; }

    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getIdCidade() { return idCidade; }
    public void setIdCidade(String idCidade) { this.idCidade = idCidade; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getImagensJson() { return imagensJson; }
    public void setImagensJson(String imagensJson) { this.imagensJson = imagensJson; }

    public Boolean getTemBomboniere() { return temBomboniere; }
    public void setTemBomboniere(Boolean temBomboniere) { this.temBomboniere = temBomboniere; }

    public Integer getTotalSalas() { return totalSalas; }
    public void setTotalSalas(Integer totalSalas) { this.totalSalas = totalSalas; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public List<Sala> getSalas() { return salas; }
    public void setSalas(List<Sala> salas) { this.salas = salas; }

    public List<CinemaAvaliacao> getAvaliacoes() { return avaliacoes; }
    public void setAvaliacoes(List<CinemaAvaliacao> avaliacoes) { this.avaliacoes = avaliacoes; }
}
