package com.maisprati.Cineseat.dto;

import com.maisprati.Cineseat.entities.PoltronaAvaliacao;

import java.time.LocalDateTime;

public class PoltronaAvaliacaoDTO {

    private Long idPoltronaAvaliacao;
    private Long poltronaId;
    private String ingressoId;

    private Integer notaConforto;
    private Integer notaBraco;
    private Integer notaVisao;

    private Double notaGeral;

    private String comentario;

    private LocalDateTime dataAvaliacao;
    private LocalDateTime dataAtualizacao;

    public PoltronaAvaliacaoDTO() {}

    public PoltronaAvaliacaoDTO(PoltronaAvaliacao entity) {
        this.idPoltronaAvaliacao = entity.getIdPoltronaAvaliacao();
        this.poltronaId = entity.getPoltrona().getIdPoltrona();
        this.ingressoId = entity.getIngressoId();

        this.notaConforto = entity.getNotaConforto();
        this.notaBraco = entity.getNotaBraco();
        this.notaVisao = entity.getNotaVisao();
        this.notaGeral = entity.getNotaGeral();
        this.comentario = entity.getComentario();

        this.dataAvaliacao = entity.getDataAvaliacao();
        this.dataAtualizacao = entity.getDataAtualizacao();
    }

    public Long getIdPoltronaAvaliacao() {return idPoltronaAvaliacao;}
    public void setIdPoltronaAvaliacao(Long idPoltronaAvaliacao) {this.idPoltronaAvaliacao = idPoltronaAvaliacao;}

    public Long getPoltronaId() {return poltronaId;}
    public void setPoltronaId(Long poltronaId) {this.poltronaId = poltronaId;}

    public String getIngressoId() {return ingressoId;}
    public void setIngressoId(String ingressoId) {this.ingressoId = ingressoId;}

    public Integer getNotaConforto() {return notaConforto;}
    public void setNotaConforto(Integer notaConforto) {this.notaConforto = notaConforto;}

    public Integer getNotaBraco() {return notaBraco;}
    public void setNotaBraco(Integer notaBraco) {this.notaBraco = notaBraco;}

    public Integer getNotaVisao() {return notaVisao;}
    public void setNotaVisao(Integer notaVisao) {this.notaVisao = notaVisao;}

    public Double getNotaGeral() {return notaGeral;}
    public void setNotaGeral(Double notaGeral) {this.notaGeral = notaGeral;}

    public String getComentario() {return comentario;}
    public void setComentario(String comentario) {this.comentario = comentario;}

    public LocalDateTime getDataAvaliacao() {return dataAvaliacao;}
    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {this.dataAvaliacao = dataAvaliacao;}

    public LocalDateTime getDataAtualizacao() {return dataAtualizacao;}
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {this.dataAtualizacao = dataAtualizacao;}
}
