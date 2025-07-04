package br.com.alura.ScreenMatch.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ScreenDados(@JsonAlias("Title") String titulo, @JsonAlias("Year") String ano,
                          @JsonAlias("imdbRating") String avaliacao, @JsonAlias("Runtime") String duracao,
                          @JsonAlias("Plot") String sinopse, @JsonAlias("Actors") String atores,
                          @JsonAlias("Poster") String poster, @JsonAlias("Genre") String genero) {
}
