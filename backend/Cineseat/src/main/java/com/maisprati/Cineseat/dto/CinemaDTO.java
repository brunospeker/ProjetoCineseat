package com.maisprati.Cineseat.dto;

import com.maisprati.Cineseat.entities.Cinema;
import java.time.LocalDateTime;

public class CinemaDTO {
    private Long idCinema;
    private String nomeCinema;
    private String site;
    private String cnpj;
    private String estado;
    private String uf;
    private String cidade;
    private String idCidade;
    private String bairro;
    private String numero;
    private String imagensJson;
    private Boolean temBomboniere;
    private Integer totalSalas;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Boolean ativo;

    public CinemaDTO() {}

    public CinemaDTO(Cinema entity) {
        this.idCinema = entity.getIdCinema();
        this.nomeCinema = entity.getNomeCinema();
        this.site = entity.getSite();
        this.cnpj = entity.getCnpj();
        this.estado = entity.getEstado();
        this.uf = entity.getUf();
        this.cidade = entity.getCidade();
        this.idCidade = entity.getIdCidade();
        this.bairro = entity.getBairro();
        this.numero = entity.getNumero();
        this.imagensJson = entity.getImagensJson();
        this.temBomboniere = entity.getTemBomboniere();
        this.totalSalas = entity.getTotalSalas();
        this.dataCriacao = entity.getDataCriacao();
        this.dataAtualizacao = entity.getDataAtualizacao();
        this.ativo = entity.getAtivo();
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
}