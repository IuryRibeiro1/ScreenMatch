package br.com.alura.ScreenMatch.repository;

import br.com.alura.ScreenMatch.entities.Categoria;
import br.com.alura.ScreenMatch.entities.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//Criando uma interface de Serie com o tipo do ID da classe que é um integer
public interface SerieRepository  extends JpaRepository<Serie, Integer> {

    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);
}


