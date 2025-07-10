package br.com.alura.ScreenMatch.entities;


import br.com.alura.ScreenMatch.service.ConsultaChatGPT;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {

   public Serie() {

   }

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(unique = true)
   private String titulo;

   private Integer totalTemporadas;

   private Double avaliacao;

   @Enumerated(EnumType.STRING)
   private Categoria genero;

   private String poster;

   private String sinopse;

   private String atores;

   @OneToMany(mappedBy = "serie")
   private List<Episodio> Episodio = new ArrayList<>();

   public Serie(DadosSerie dadosSerie) {
      this.titulo = dadosSerie.titulo();
      this.totalTemporadas = dadosSerie.totalTemporadas();
      this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
      this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
      this.atores = dadosSerie.atores();
      this.poster = dadosSerie.poster();
      this.sinopse = ConsultaChatGPT.obterTraducao(dadosSerie.sinopse().trim());
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getTitulo() {
      return titulo;
   }

   public void setTitulo(String titulo) {
      this.titulo = titulo;
   }

   public Integer getTotalTemporadas() {
      return totalTemporadas;
   }

   public void setTotalTemporadas(Integer totalTemporadas) {
      this.totalTemporadas = totalTemporadas;
   }

   public Double getAvaliacao() {
      return avaliacao;
   }

   public void setAvaliacao(Double avaliacao) {
      this.avaliacao = avaliacao;
   }

   public Categoria getGenero() {
      return genero;
   }

   public void setGenero(Categoria genero) {
      this.genero = genero;
   }

   public String getPoster() {
      return poster;
   }

   public void setPoster(String poster) {
      this.poster = poster;
   }

   public String getSinopse() {
      return sinopse;
   }

   public void setSinopse(String sinopse) {
      this.sinopse = sinopse;
   }

   public String getAtores() {
      return atores;
   }

   public void setAtores(String atores) {
      this.atores = atores;
   }

   public List<Episodio> getEpisodio() {
      return Episodio;
   }

   public void setEpisodio(List<Episodio> episodio) {
      Episodio = episodio;
   }

   @Override
   public String toString() {
      return  "Genero=" + genero +
              ",titulo='" + titulo + '\'' +
              ", totalTemporadas=" + totalTemporadas +
              ", avaliacao=" + avaliacao +
              ", genero=" + genero +
              ", poster='" + poster + '\'' +
              ", sinopse='" + sinopse + '\'' +
              ", atores='" + atores + '\'';
   }
}
