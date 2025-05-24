package br.com.alura.ScreenMatch.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ScreenDados(@JsonAlias("Title") String titulo, @JsonAlias("Year") String ano, @JsonAlias("imdbRating") String avaliacao) {
}
