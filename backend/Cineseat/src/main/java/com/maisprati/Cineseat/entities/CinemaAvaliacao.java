package com.maisprati.Cineseat.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "cinema_avaliacoes")
public class CinemaAvaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao_cinema")
    private Long idAvaliacaoCinema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cinema", nullable = false)
    private Cinema cinema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    @Column(name = "id_ingresso", unique = true, nullable = false, length = 20)
    private String idIngresso;

    @Column(nullable = false)
    private Integer notaGeral;

    private Integer notaLimpeza;
    private Integer notaAtendimento;
    private Integer notaPreco;
    private Integer notaAlimentacao;

    @Column(length = 1000)
    private String comentario;

    @Column(nullable = false, name = "data_avaliacao")
    private LocalDateTime dataAvaliacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    public CinemaAvaliacao() {
    }

    public CinemaAvaliacao(Cinema cinema, User usuario, Integer notaGeral) {
        this.cinema = cinema;
        this.usuario = usuario;
        this.notaGeral = notaGeral;
    }

    public CinemaAvaliacao(Cinema cinema, User usuario, String idIngresso, Integer notaGeral,
                           Integer notaLimpeza, Integer notaAtendimento, Integer notaPreco,
                           Integer notaAlimentacao, String comentario) {
        this.cinema = cinema;
        this.usuario = usuario;
        this.idIngresso = idIngresso;
        this.notaGeral = notaGeral;
        this.notaLimpeza = notaLimpeza;
        this.notaAtendimento = notaAtendimento;
        this.notaPreco = notaPreco;
        this.notaAlimentacao = notaAlimentacao;
        this.comentario = comentario;
    }

    @PrePersist
    public void inicializarDatas() {
        this.dataAvaliacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    public void atualizar() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Long getIdAvaliacaoCinema() { return idAvaliacaoCinema; }
    public void setIdAvaliacaoCinema(Long idAvaliacaoCinema) { this.idAvaliacaoCinema = idAvaliacaoCinema; }

    public Cinema getCinema() { return cinema; }
    public void setCinema(Cinema cinema) { this.cinema = cinema; }

    public User getUsuario() { return usuario; }
    public void setUsuario(User usuario) { this.usuario = usuario; }

    public String getIdIngresso() { return idIngresso; }
    public void setIdIngresso(String idIngresso) {this.idIngresso = idIngresso;}

    public Integer getNotaGeral() { return notaGeral; }
    public void setNotaGeral(Integer notaGeral) {
        if (notaGeral != null && (notaGeral < 1 || notaGeral > 5)) {
            throw new IllegalArgumentException("A nota geral deve estar entre 1 e 5");
        }
        this.notaGeral = notaGeral;
    }

    public Integer getNotaLimpeza() { return notaLimpeza; }
    public void setNotaLimpeza(Integer notaLimpeza) {
        if (notaLimpeza != null && (notaLimpeza < 1 || notaLimpeza > 5)) {
            throw new IllegalArgumentException("A nota da limpeza deve estar entre 1 e 5");
        }
        this.notaLimpeza = notaLimpeza;
    }

    public Integer getNotaAtendimento() { return notaAtendimento; }
    public void setNotaAtendimento(Integer notaAtendimento) {
        if (notaAtendimento != null && (notaAtendimento < 1 || notaAtendimento > 5)) {
            throw new IllegalArgumentException("A nota do atendimento deve estar entre 1 e 5");
        }
        this.notaAtendimento = notaAtendimento;
    }

    public Integer getNotaPreco() { return notaPreco; }
    public void setNotaPreco(Integer notaPreco) {
        if (notaPreco != null && (notaPreco < 1 || notaPreco > 5)) {
            throw new IllegalArgumentException("A nota do preço deve estar entre 1 e 5");
        }
        this.notaPreco = notaPreco;
    }

    public Integer getNotaAlimentacao() { return notaAlimentacao;}
    public void setNotaAlimentacao(Integer notaAlimentacao) {
        if (notaAlimentacao != null && (notaAlimentacao < 1 || notaAlimentacao > 5)) {
            throw new IllegalArgumentException("A nota da alimentação deve estar entre 1 e 5");
        }
        this.notaAlimentacao = notaAlimentacao;
    }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public LocalDateTime getDataAvaliacao() { return dataAvaliacao; }
    public void setDataAvaliacao(LocalDateTime dataAvaliacao) { this.dataAvaliacao = dataAvaliacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; } 
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}