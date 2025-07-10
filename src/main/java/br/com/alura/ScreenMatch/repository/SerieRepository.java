package br.com.alura.ScreenMatch.repository;

import br.com.alura.ScreenMatch.entities.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
//Criando uma interface de Serie com o tipo do ID da classe que é um integer
public interface SerieRepository  extends JpaRepository<Serie, Integer> {
}
