package br.com.alura.ScreenMatch.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,@JsonAlias("Released") String ano,@JsonAlias("Episode") Integer episodio,
                         @JsonAlias("imdbRating") String avaliacao, @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("Genre") String genero, @JsonAlias("Actors") String atores,
                         @JsonAlias("Poster") String poster, @JsonAlias("Plot") String sinopse){

}
