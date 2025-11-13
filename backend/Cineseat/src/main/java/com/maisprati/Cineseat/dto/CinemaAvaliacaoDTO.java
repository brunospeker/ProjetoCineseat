package com.maisprati.Cineseat.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maisprati.Cineseat.entities.CinemaAvaliacao;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CinemaAvaliacaoDTO {
    private Long idAvaliacaoCinema;
    private Long idCinema;
    private Long idUsuario;
    private String idIngresso;
    private Double notaGeral;
    private Integer notaLimpeza;
    private Integer notaAtendimento;
    private Integer notaPreco;
    private Integer notaAlimentacao;
    private String comentario;
    private LocalDateTime dataAvaliacao;
    private LocalDateTime dataAtualizacao;

    public CinemaAvaliacaoDTO() {
    }

    public CinemaAvaliacaoDTO(CinemaAvaliacao entity) {
        this.idAvaliacaoCinema = entity.getIdAvaliacaoCinema();
        this.idCinema = entity.getCinema().getIdCinema();
        this.idUsuario = entity.getUsuario().getId();
        this.idIngresso = entity.getIdIngresso();
        this.notaGeral = entity.getNotaGeral();
        this.notaLimpeza = entity.getNotaLimpeza();
        this.notaAtendimento = entity.getNotaAtendimento();
        this.notaPreco = entity.getNotaPreco();
        this.notaAlimentacao = entity.getNotaAlimentacao();
        this.comentario = entity.getComentario();
        this.dataAvaliacao = entity.getDataAvaliacao();
        this.dataAtualizacao = entity.getDataAtualizacao();
    }

    public Long getIdAvaliacaoCinema() {return idAvaliacaoCinema;}
    public void setIdAvaliacaoCinema(Long idAvaliacaoCinema) {this.idAvaliacaoCinema = idAvaliacaoCinema;}

    public Long getIdCinema() {return idCinema;}
    public void setIdCinema(Long idCinema) {this.idCinema = idCinema;}

    public Long getIdUsuario() {return idUsuario;}
    public void setIdUsuario(Long idUsuario) {this.idUsuario = idUsuario;}

    public String getIdIngresso() {return idIngresso;}
    public void setIdIngresso(String idIngresso) {this.idIngresso = idIngresso;}

    public Double getNotaGeral() {return notaGeral;}
    public void setNotaGeral(Double notaGeral) {this.notaGeral = notaGeral;}

    public Integer getNotaLimpeza() {return notaLimpeza;}
    public void setNotaLimpeza(Integer notaLimpeza) {this.notaLimpeza = notaLimpeza;}

    public Integer getNotaAtendimento() {return notaAtendimento;}
    public void setNotaAtendimento(Integer notaAtendimento) {this.notaAtendimento = notaAtendimento;}

    public Integer getNotaPreco() {return notaPreco;}
    public void setNotaPreco(Integer notaPreco) {this.notaPreco = notaPreco;}

    public Integer getNotaAlimentacao() {return notaAlimentacao;}
    public void setNotaAlimentacao(Integer notaAlimentacao) {this.notaAlimentacao = notaAlimentacao;}

    public String getComentario() {return comentario;}
    public void setComentario(String comentario) {this.comentario = comentario;}

    public LocalDateTime getDataAvaliacao() {return dataAvaliacao;}
    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {this.dataAvaliacao = dataAvaliacao;}

    public LocalDateTime getDataAtualizacao() {return dataAtualizacao;}
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {this.dataAtualizacao = dataAtualizacao;}
}
