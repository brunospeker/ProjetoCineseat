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
    private Double notaGeral;

    @Column(nullable = false)
    private Integer notaLimpeza;

    @Column(nullable = false)
    private Integer notaAtendimento;

    @Column(nullable = false)
    private Integer notaPreco;

    @Column(nullable = false)
    private Integer notaAlimentacao;

    @Column(length = 1000)
    private String comentario;

    @Column(nullable = false, name = "data_avaliacao")
    private LocalDateTime dataAvaliacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    public CinemaAvaliacao() {
    }

    public CinemaAvaliacao(Cinema cinema, User usuario, String idIngresso,
                           Integer notaLimpeza, Integer notaAtendimento, Integer notaPreco,
                           Integer notaAlimentacao, String comentario) {
        this.cinema = cinema;
        this.usuario = usuario;
        this.idIngresso = idIngresso;
        this.notaLimpeza = notaLimpeza;
        this.notaAtendimento = notaAtendimento;
        this.notaPreco = notaPreco;
        this.notaAlimentacao = notaAlimentacao;
        this.comentario = comentario;
    }

    @PrePersist
    public void aoCriar() {
        calcularNotaGeral();
        this.dataAvaliacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    public void aoAtualizar() {
        calcularNotaGeral();
        this.dataAtualizacao = LocalDateTime.now();
    }

    private void calcularNotaGeral() {
        int soma = 0;
        int count = 0;

        if (notaLimpeza != null) { soma += notaLimpeza; count++; }
        if (notaAtendimento != null) { soma += notaAtendimento; count++; }
        if (notaPreco != null) { soma += notaPreco; count++; }
        if (notaAlimentacao != null) { soma += notaAlimentacao; count++; }

        this.notaGeral = (count > 0) ? ((double) soma / count) : null;
    }

    public Long getIdAvaliacaoCinema() {return idAvaliacaoCinema;}
    public void setIdAvaliacaoCinema(Long idAvaliacaoCinema) {this.idAvaliacaoCinema = idAvaliacaoCinema;}

    public Cinema getCinema() {return cinema;}
    public void setCinema(Cinema cinema) {this.cinema = cinema;}

    public User getUsuario() {return usuario;}
    public void setUsuario(User usuario) {this.usuario = usuario;}

    public String getIdIngresso() {return idIngresso;}
    public void setIdIngresso(String idIngresso) {this.idIngresso = idIngresso;}

    public Double getNotaGeral() {return notaGeral;}

    public Integer getNotaLimpeza() {return notaLimpeza;}
    public void setNotaLimpeza(Integer notaLimpeza) {
        validarNota("limpeza", notaLimpeza);
        this.notaLimpeza = notaLimpeza;
        calcularNotaGeral();
    }

    public Integer getNotaAtendimento() {return notaAtendimento;}
    public void setNotaAtendimento(Integer notaAtendimento) {
        validarNota("atendimento", notaAtendimento);
        this.notaAtendimento = notaAtendimento;
        calcularNotaGeral();
    }

    public Integer getNotaPreco() {return notaPreco;}
    public void setNotaPreco(Integer notaPreco) {
        validarNota("preço", notaPreco);
        this.notaPreco = notaPreco;
        calcularNotaGeral();
    }

    public Integer getNotaAlimentacao() {return notaAlimentacao;}
    public void setNotaAlimentacao(Integer notaAlimentacao) {
        validarNota("alimentação", notaAlimentacao);
        this.notaAlimentacao = notaAlimentacao;
        calcularNotaGeral();
    }

    public String getComentario() {return comentario;}
    public void setComentario(String comentario) {this.comentario = comentario;}

    public LocalDateTime getDataAvaliacao() {return dataAvaliacao;}
    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {this.dataAvaliacao = dataAvaliacao;}

    public LocalDateTime getDataAtualizacao() {return dataAtualizacao;}
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {this.dataAtualizacao = dataAtualizacao;}

    private void validarNota(String campo, Integer valor) {
        if (valor != null && (valor < 1 || valor > 5)) {
            throw new IllegalArgumentException("A nota de " + campo + " deve estar entre 1 e 5");
        }
    }
}
