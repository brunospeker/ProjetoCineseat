package com.maisprati.Cineseat.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PoltronaAvaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPoltronaAvaliacao;

    @ManyToOne
    @JoinColumn(name = "poltrona_id", nullable = false)
    private Poltrona poltrona;

    @Column(name = "ingresso_id", unique = true)
    private String ingressoId;

    @Column(nullable = false)
    private Integer notaConforto;

    @Column(nullable = false)
    private Integer notaBraco;

    @Column(nullable = false)
    private Integer notaVisao;

    @Column(nullable = false)
    private Double notaGeral;

    @Column(length = 1000)
    private String comentario;

    @Column(nullable = false, name = "data_avaliacao")
    private LocalDateTime dataAvaliacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    public PoltronaAvaliacao() {}

    public PoltronaAvaliacao(Poltrona poltrona, String ingressoId, Integer notaConforto,
                             Integer notaBraco, Integer notaVisao, String comentario) {
        this.poltrona = poltrona;
        this.ingressoId = ingressoId;
        this.notaConforto = notaConforto;
        this.notaBraco = notaBraco;
        this.notaVisao = notaVisao;
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

        if (notaConforto != null) { soma += notaConforto; count++; }
        if (notaBraco != null) { soma += notaBraco; count++; }
        if (notaVisao != null) { soma += notaVisao; count++; }

        this.notaGeral = (count > 0) ? ((double) soma / count) : null;
    }

    private void validarNota(String campo, Integer valor) {
        if (valor != null && (valor < 1 || valor > 5)) {
            throw new IllegalArgumentException("A nota de " + campo + " deve estar entre 1 e 5");
        }
    }

    public Long getIdPoltronaAvaliacao() {return idPoltronaAvaliacao;}
    public void setIdPoltronaAvaliacao(Long idPoltronaAvaliacao) {this.idPoltronaAvaliacao = idPoltronaAvaliacao;}

    public Poltrona getPoltrona() {return poltrona;}
    public void setPoltrona(Poltrona poltrona) {this.poltrona = poltrona;}

    public String getIngressoId() {return ingressoId;}
    public void setIngressoId(String ingressoId) {this.ingressoId = ingressoId;}

    public Integer getNotaConforto() {return notaConforto;}
    public void setNotaConforto(Integer notaConforto) {
        validarNota("conforto", notaConforto);
        this.notaConforto = notaConforto;
        calcularNotaGeral();
    }

    public Integer getNotaBraco() {return notaBraco;}

    public void setNotaBraco(Integer notaBraco) {
        validarNota("braço", notaBraco);
        this.notaBraco = notaBraco;
        calcularNotaGeral();
    }

    public Integer getNotaVisao() {return notaVisao;}
    public void setNotaVisao(Integer notaVisao) {
        validarNota("visão", notaVisao);
        this.notaVisao = notaVisao;
        calcularNotaGeral();
    }

    public Double getNotaGeral() {return notaGeral;}


    public String getComentario() {return comentario;}
    public void setComentario(String comentario) {this.comentario = comentario;}

    public LocalDateTime getDataAvaliacao() {return dataAvaliacao;}
    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {this.dataAvaliacao = dataAvaliacao;}

    public LocalDateTime getDataAtualizacao() {return dataAtualizacao;}
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {this.dataAtualizacao = dataAtualizacao;}
}
