package br.com.alura.ScreenMatch.service;

import br.com.alura.ScreenMatch.dto.EpisodioDTO;
import br.com.alura.ScreenMatch.dto.SerieDTO;
import br.com.alura.ScreenMatch.entities.Categoria;
import br.com.alura.ScreenMatch.entities.Serie;
import br.com.alura.ScreenMatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {


    @Autowired
    private SerieRepository serieRepository;

    @GetMapping("/series")
    public List<SerieDTO> obterSeries() {
        return converterDTO(serieRepository.findAll());

    }

    @GetMapping("/series/top5")
    public List<SerieDTO> obterTopSeries() {
        return converterDTO(serieRepository.findTop5ByOrderByAvaliacaoDesc());

    }

    private List<SerieDTO> converterDTO(List<Serie> series) {
        return serieRepository.findAll().stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getPoster(), s.getSinopse(), s.getAtores()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterTopCincoSeries() {
        return converterDTO(serieRepository.lancamentosRecentes());
    }

    public SerieDTO obterPorId(Integer id) {
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getPoster(), s.getSinopse(), s.getAtores());
        }
        return null;
    }

    public List<EpisodioDTO> obterTotalTemporadas(Integer id) {
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodio().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obterEpisodiosPorNumero(Integer id, Integer numero){
        return serieRepository.obterEpisodiosPorTemporada(id, numero)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterSeriePorCatergoria(String nome) {
        Categoria categoria = Categoria.fromPortugues(nome);
        return  converterDTO(serieRepository.findByGenero(categoria));

    }
}
