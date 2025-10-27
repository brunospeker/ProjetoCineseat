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
    private Integer notaGeral;
    private Integer notaLimpeza;
    private Integer notaAtendimento;
    private Integer notaPreco;
    private Integer notaAlimentacao;
    private String comentario;
    private LocalDateTime dataAvaliacao;
    private LocalDateTime dataAtualizacao;

    private Double mediaGeral;
    private Double mediaLimpeza;
    private Double mediaAtendimento;
    private Double mediaPreco;
    private Double mediaAlimentacao;

    public CinemaAvaliacaoDTO() {}

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

 public CinemaAvaliacaoDTO(Long idCinema,
                              Double mediaGeral,
                              Double mediaLimpeza,
                              Double mediaPreco,
                              Double mediaAlimentacao,
                              Double mediaAtendimento) {
        this.idCinema = idCinema;
        this.mediaGeral = mediaGeral;
        this.mediaLimpeza = mediaLimpeza;
        this.mediaPreco = mediaPreco;
        this.mediaAlimentacao = mediaAlimentacao;
        this.mediaAtendimento = mediaAtendimento;
    }

    public Long getIdAvaliacaoCinema() { return idAvaliacaoCinema; }
    public void setIdAvaliacaoCinema(Long idAvaliacaoCinema) { this.idAvaliacaoCinema = idAvaliacaoCinema; }

    public Long getIdCinema() { return idCinema; }
    public void setIdCinema(Long idCinema) { this.idCinema = idCinema; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getIdIngresso() { return idIngresso; }
    public void setIdIngresso(String idIngresso) {this.idIngresso = idIngresso;}

    public Integer getNotaGeral() { return notaGeral; }
    public void setNotaGeral(Integer notaGeral) { this.notaGeral = notaGeral; }

    public Integer getNotaLimpeza() { return notaLimpeza; }
    public void setNotaLimpeza(Integer notaLimpeza) { this.notaLimpeza = notaLimpeza; }

    public Integer getNotaAtendimento() { return notaAtendimento; }
    public void setNotaAtendimento(Integer notaAtendimento) { this.notaAtendimento = notaAtendimento; }

    public Integer getNotaPreco() { return notaPreco; }
    public void setNotaPreco(Integer notaPreco) { this.notaPreco = notaPreco; }

    public Integer getNotaAlimentacao() { return notaAlimentacao; }
    public void setNotaAlimentacao(Integer notaAlimentacao) { this.notaAlimentacao = notaAlimentacao; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public LocalDateTime getDataAvaliacao() { return dataAvaliacao; }
    public void setDataAvaliacao(LocalDateTime dataAvaliacao) { this.dataAvaliacao = dataAvaliacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public Double getMediaGeral() { return mediaGeral; }
    public void setMediaGeral(Double mediaGeral) { this.mediaGeral = mediaGeral; }

    public Double getMediaLimpeza() { return mediaLimpeza; }
    public void setMediaLimpeza(Double mediaLimpeza) { this.mediaLimpeza = mediaLimpeza; }

    public Double getMediaAtendimento() { return mediaAtendimento; }
    public void setMediaAtendimento(Double mediaAtendimento) { this.mediaAtendimento = mediaAtendimento; }

    public Double getMediaPreco() { return mediaPreco; }
    public void setMediaPreco(Double mediaPreco) { this.mediaPreco = mediaPreco; }

    public Double getMediaAlimentacao() { return mediaAlimentacao; }
    public void setMediaAlimentacao(Double mediaAlimentacao) { this.mediaAlimentacao = mediaAlimentacao; }

}
