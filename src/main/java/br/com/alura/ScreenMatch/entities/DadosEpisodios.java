package br.com.alura.ScreenMatch.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodios(@JsonAlias("Title") String titulo,@JsonAlias("Episode") Integer episodio,@JsonAlias("imdbRating") String avaliacao,@JsonAlias("Released") String lancamento) {
}
