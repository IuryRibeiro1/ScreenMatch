package br.com.alura.ScreenMatch.repository;

import br.com.alura.ScreenMatch.entities.Categoria;
import br.com.alura.ScreenMatch.entities.Episodio;
import br.com.alura.ScreenMatch.entities.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

//Criando uma interface de Serie com o tipo do ID da classe que é um integer
public interface SerieRepository  extends JpaRepository<Serie, Integer> {

    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    @Query("select s from Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :avaliacao ")
    List<Serie> seriePorTemporadaEAvaliacao(int totalTemporadas, double avaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodio> episodiosPorTrecho(String trechoEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 2")
    List<Episodio> topEpisodiosPorSerie(Serie serie);

    @Query("SELECT s FROM Serie s " +
    "JOIN s.episodios e " +
    "GROUP BY s " +
    "ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    List<Serie> lancamentosRecentes();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numero")
    List<Episodio> obterEpisodiosPorTemporada(Integer id, Integer numero);
}


