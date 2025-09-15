package com.maisprati.cineseat.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sala_avaliacoes")
public class SalaAvaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @Column(nullable = false)
    private Integer nota; // 1 a 5 estrelas

    // Avaliações específicas por categoria
    @Column(name = "nota_conforto")
    private Integer notaConforto; // 1 a 5 - conforto das poltronas

    @Column(name = "nota_som")
    private Integer notaSom; // 1 a 5 - qualidade do som

    @Column(name = "nota_imagem")
    private Integer notaImagem; // 1 a 5 - qualidade da imagem/tela

    @Column(name = "nota_limpeza")
    private Integer notaLimpeza; // 1 a 5 - limpeza da sala

    @Column(name = "nota_temperatura")
    private Integer notaTemperatura; // 1 a 5 - temperatura ambiente

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    private Boolean ativa = true;

    // Para controlar se a avaliação foi útil para outros usuários
    @Column(name = "likes_count")
    private Integer likesCount = 0;

    @Column(name = "dislikes_count")
    private Integer dislikesCount = 0;

    // Construtores
    public SalaAvaliacao() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public SalaAvaliacao(Sala sala, User usuario, Integer nota) {
        this();
        this.sala = sala;
        this.usuario = usuario;
        this.nota = nota;
    }

    public SalaAvaliacao(Sala sala, User usuario, Integer nota, String comentario) {
        this(sala, usuario, nota);
        this.comentario = comentario;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("Nota deve estar entre 1 e 5");
        }
        this.nota = nota;
    }

    public Integer getNotaConforto() {
        return notaConforto;
    }

    public void setNotaConforto(Integer notaConforto) {
        if (notaConforto != null && (notaConforto < 1 || notaConforto > 5)) {
            throw new IllegalArgumentException("Nota de conforto deve estar entre 1 e 5");
        }
        this.notaConforto = notaConforto;
    }

    public Integer getNotaSom() {
        return notaSom;
    }

    public void setNotaSom(Integer notaSom) {
        if (notaSom != null && (notaSom < 1 || notaSom > 5)) {
            throw new IllegalArgumentException("Nota de som deve estar entre 1 e 5");
        }
        this.notaSom = notaSom;
    }

    public Integer getNotaImagem() {
        return notaImagem;
    }

    public void setNotaImagem(Integer notaImagem) {
        if (notaImagem != null && (notaImagem < 1 || notaImagem > 5)) {
            throw new IllegalArgumentException("Nota de imagem deve estar entre 1 e 5");
        }
        this.notaImagem = notaImagem;
    }

    public Integer getNotaLimpeza() {
        return notaLimpeza;
    }

    public void setNotaLimpeza(Integer notaLimpeza) {
        if (notaLimpeza != null && (notaLimpeza < 1 || notaLimpeza > 5)) {
            throw new IllegalArgumentException("Nota de limpeza deve estar entre 1 e 5");
        }
        this.notaLimpeza = notaLimpeza;
    }

    public Integer getNotaTemperatura() {
        return notaTemperatura;
    }

    public void setNotaTemperatura(Integer notaTemperatura) {
        if (notaTemperatura != null && (notaTemperatura < 1 || notaTemperatura > 5)) {
            throw new IllegalArgumentException("Nota de temperatura deve estar entre 1 e 5");
        }
        this.notaTemperatura = notaTemperatura;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(Integer dislikesCount) {
        this.dislikesCount = dislikesCount;
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Método para calcular nota média baseada nas categorias
    public Double calcularNotaMedia() {
        int totalNotas = 0;
        int quantidadeNotas = 0;

        if (notaConforto != null) {
            totalNotas += notaConforto;
            quantidadeNotas++;
        }
        if (notaSom != null) {
            totalNotas += notaSom;
            quantidadeNotas++;
        }
        if (notaImagem != null) {
            totalNotas += notaImagem;
            quantidadeNotas++;
        }
        if (notaLimpeza != null) {
            totalNotas += notaLimpeza;
            quantidadeNotas++;
        }
        if (notaTemperatura != null) {
            totalNotas += notaTemperatura;
            quantidadeNotas++;
        }

        return quantidadeNotas > 0 ? (double) totalNotas / quantidadeNotas : null;
    }

    // Método para incrementar likes
    public void incrementarLikes() {
        this.likesCount = (this.likesCount == null) ? 1 : this.likesCount + 1;
    }

    // Método para incrementar dislikes
    public void incrementarDislikes() {
        this.dislikesCount = (this.dislikesCount == null) ? 1 : this.dislikesCount + 1;
    }
}