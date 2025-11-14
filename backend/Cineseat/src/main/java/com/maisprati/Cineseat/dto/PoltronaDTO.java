package com.maisprati.Cineseat.dto;

import com.maisprati.Cineseat.entities.Poltrona;

import java.time.LocalDateTime;

public class PoltronaDTO {
    private Long idPoltrona;
    private String fila;
    private String coluna;
    private int tipo;
    private Double mediaGeral;
    private Long idSala;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public PoltronaDTO() {}

    public PoltronaDTO(Poltrona entity) {
        this.idPoltrona = entity.getIdPoltrona();
        this.fila = entity.getFila();
        this.coluna = entity.getColuna();
        this.tipo = entity.getTipo();
        this.mediaGeral = entity.getMediaGeral();
        this.idSala = entity.getSala() != null ? entity.getSala().getIdSala() : null;
        this.dataCriacao = entity.getDataCriacao();
        this.dataAtualizacao = entity.getDataAtualizacao();
    }

    public PoltronaDTO(Long idPoltrona, Double mediaGeral) {
        this.idPoltrona = idPoltrona;
        this.mediaGeral = mediaGeral;
    }

    public Long getIdPoltrona() { return idPoltrona; }
    public void setIdPoltrona(Long idPoltrona) { this.idPoltrona = idPoltrona; }

    public String getFila() { return fila; }
    public void setFila(String fila) { this.fila = fila; }

    public String getColuna() { return coluna; }
    public void setColuna(String coluna) { this.coluna = coluna; }

    public int getTipo() { return tipo; }
    public void setTipo(int tipo) { this.tipo = tipo; }

    public Double getMediaGeral() { return mediaGeral; }
    public void setMediaGeral(Double mediaGeral) { this.mediaGeral = mediaGeral; }

    public Long getIdSala() { return idSala; }
    public void setIdSala(Long idSala) { this.idSala = idSala; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}
