package com.maisprati.Cineseat.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "poltronas")
public class Poltrona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_poltrona")
    private Long idPoltrona;

    @Column(nullable = false)
    private String fila;

    @Column(nullable = false, length = 5)
    private String coluna;

    private int tipo;
    // ---- SIGNIFICADO DE CADA RESPOSTA DO TIPO ----
    // 0 - REGULAR
    // 1 - VIP
    // 2 - NAMORADEIRA
    // 3 - CADEIRANTE
    // 4 - ACOMPANHANTE_CADEIRANTE
    // 5 - MOBILIDADE_REDUZIDA
    // 6 - OBESO

    @Column(name = "media_geral")
    private Double mediaGeral;

    @OneToMany(mappedBy = "poltrona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PoltronaAvaliacao> avaliacoes;

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    public Poltrona() {}

    public Poltrona(String fila, String coluna, int tipo, Double mediaGeral, Sala sala) {
        this.fila = fila;
        this.coluna = coluna;
        this.tipo = tipo;
        this.mediaGeral = mediaGeral;
        this.sala = sala;
    }

    @PrePersist
    @PreUpdate
    public void atualizarDadosAutomaticos() {
        this.dataAtualizacao = LocalDateTime.now();
        if (this.dataCriacao == null) {
            this.dataCriacao = LocalDateTime.now();
        }
    }

    public Long getIdPoltrona() {return idPoltrona;}
    public void setIdPoltrona(Long idPoltrona) {this.idPoltrona = idPoltrona;}

    public String getFila() {return fila;}
    public void setFila(String fila) {this.fila = fila;}

    public String getColuna() {return coluna;}
    public void setColuna(String coluna) {this.coluna = coluna;}

    public int getTipo() {return tipo;}
    public void setTipo(int tipo) {this.tipo = tipo;}

    public Double getMediaGeral() {return mediaGeral;}
    public void setMediaGeral(Double mediaGeral) {this.mediaGeral = mediaGeral;}

    public List<PoltronaAvaliacao> getAvaliacoes() { return avaliacoes; }
    public void setAvaliacoes(List<PoltronaAvaliacao> avaliacoes) { this.avaliacoes = avaliacoes; }

    public Sala getSala() {return sala;}
    public void setSala(Sala sala) {this.sala = sala;}

    public LocalDateTime getDataCriacao() {return dataCriacao;}
    public void setDataCriacao(LocalDateTime dataCriacao) {this.dataCriacao = dataCriacao;}

    public LocalDateTime getDataAtualizacao() {return dataAtualizacao;}
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {this.dataAtualizacao = dataAtualizacao;}

}
