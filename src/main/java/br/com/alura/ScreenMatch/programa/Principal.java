package br.com.alura.ScreenMatch.programa;

import br.com.alura.ScreenMatch.entities.DadosTemporadas;
import br.com.alura.ScreenMatch.entities.ScreenDados;
import br.com.alura.ScreenMatch.entities.DadosSerie;
import br.com.alura.ScreenMatch.entities.Serie;
import br.com.alura.ScreenMatch.service.ConverterDados;
import br.com.alura.ScreenMatch.service.ScreenService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final String ENDEREÇO = "http://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=a64d765c";

    private ScreenService screenService = new ScreenService();
    private ConverterDados converterDados = new ConverterDados();

    private List<DadosSerie> dadosSeries = new ArrayList<>();

    Scanner sc = new Scanner(System.in);


    int opcao = 1;


    public void exibeMenu() {

        while (opcao != 0) {

            System.out.println("MENU: " +
                    "\n1: Buscar séries " +
                    "\n2: Buscar filmes" +
                    "\n3: Buscar episódios " +
                    "\n4: Filtrar séries buscadas" +
                    "\n0: Sair");

            int escolha = sc.nextInt();

            if (escolha == 1) {
                buscarSerieWeb();
            }
            else if(escolha == 2){
                buscaFilme();
            }
            else if (escolha == 3) {
                buscarEpisodioPorSerie();
            }
            else if (escolha == 4) {
                listarSeriesBuscadas();
            } else {
                opcao = 0;
            }
        }
    }


    private void buscaFilme() {

        System.out.println("Insira o nome de um filme para busca: ");
        sc.nextLine();
        var nomeFilme = sc.nextLine();
        var json = screenService.obterDados(ENDEREÇO + nomeFilme.replace(" ", "+") + APIKEY);
        ScreenDados screenDados = converterDados.obterDados(json, ScreenDados.class);
        System.out.println(screenDados);

    }

    public void buscarSerieWeb() {
        DadosSerie dados = buscaSerie();
        dadosSeries.add(dados);
        System.out.println(dados);
    }

    public DadosSerie buscaSerie() {

        System.out.println("Insira o nome de uma série: ");
        sc.nextLine();
        String nomeSerie = sc.nextLine();

        var json = screenService.obterDados(ENDEREÇO + nomeSerie.replace(" ", "+") + APIKEY);
        DadosSerie dadosSerie = converterDados.obterDados(json, DadosSerie.class);

        return dadosSerie;

    }

    public void buscarEpisodioPorSerie() {
        DadosSerie dadosSerie = buscaSerie();
        List<DadosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var json = screenService.obterDados(ENDEREÇO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + APIKEY);
            DadosTemporadas dadosTemporadas = converterDados.obterDados(json, DadosTemporadas.class);
            temporadas.add(dadosTemporadas);
        }
        temporadas.forEach(System.out::println);

    }
    public void listarSeriesBuscadas(){
        List<Serie> serie = new ArrayList<>();
        serie = dadosSeries.stream()
                        .map(s -> new Serie(s))
                                .collect(Collectors.toList());
            serie.stream()
                    .sorted(Comparator.comparing(Serie::getGenero))
                    .forEach(System.out::println);
            opcao = 0;
    }

}


