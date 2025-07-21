package br.com.alura.ScreenMatch.controller;

import br.com.alura.ScreenMatch.dto.EpisodioDTO;
import br.com.alura.ScreenMatch.dto.SerieDTO;
import br.com.alura.ScreenMatch.entities.Serie;
import br.com.alura.ScreenMatch.repository.SerieRepository;
import br.com.alura.ScreenMatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping()
    public List<SerieDTO> obterSeries() {
    return serieService.obterSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTopSeries(){
        return serieService.obterTopSeries();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos(){
        return serieService.obterTopCincoSeries();
    }
//  Utiliza entre chaves para indicar que vai aparecer um número selecionado pelo usuário
    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Integer id){
        return serieService.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTotalTemporadas(@PathVariable Integer id){
        return  serieService.obterTotalTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterEpisodiosPorNumero(@PathVariable Integer id, @PathVariable Integer numero){
        return serieService.obterEpisodiosPorNumero(id, numero);
    }

    @GetMapping("/categoria/{nome}")
    public List<SerieDTO> obterSeriePorCategoria(@PathVariable String nome){
        return  serieService.obterSeriePorCatergoria(nome);
    }
}
