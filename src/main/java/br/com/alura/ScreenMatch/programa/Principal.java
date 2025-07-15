package br.com.alura.ScreenMatch.programa;

import br.com.alura.ScreenMatch.entities.*;
import br.com.alura.ScreenMatch.repository.SerieRepository;
import br.com.alura.ScreenMatch.service.ConverterDados;
import br.com.alura.ScreenMatch.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private final String ENDEREÇO = "http://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=a64d765c";

    private ScreenService screenService = new ScreenService();
    private ConverterDados converterDados = new ConverterDados();

    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private List<Serie> series = new ArrayList<>();

    private Optional<Serie> serieBusca;

    Scanner sc = new Scanner(System.in);

    int opcao = 1;


    SerieRepository serieRepository;

    public Principal(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public void exibeMenu() {

        while (opcao != 0) {

            System.out.println("MENU: " +
                    "\n1: Buscar séries " +
                    "\n2: Buscar episódios " +
                    "\n3: Filtrar séries buscadas" +
                    "\n4: Buscar série por título" +
                    "\n5: Buscar séries por ator" +
                    "\n6: Top 5 séries" +
                    "\n7: Buscar série por gênero" +
                    "\n0: Sair");

            int escolha = sc.nextInt();

            if (escolha == 1) {
                buscarSerieWeb();
            }
            else if(escolha == 2){
                buscarEpisodioPorSerie();
            }
            else if (escolha == 3) {
                listarSeriesBuscadas();
            }
            else if (escolha == 4) {
                buscarSeriePorTitulo();
            }
            else if(escolha == 5){
                buscarSeriePorAtor();
            }
            else if(escolha == 6){
                topCincoSeries();
            }
            else if(escolha ==7){
                buscarPorGenero();
            }
            else {
                opcao = 0;
            }
        }
    }


    @Autowired
    public void buscarSerieWeb() {
        DadosSerie dados = buscaSerie();
        Serie serie = new Serie(dados);
        serieRepository.save(serie);
        System.out.println(serie);
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
        listarSeriesBuscadas();
        System.out.println("\nEscolha uma série: ");
        sc.nextLine();
        String nomeSerie = sc.nextLine();

              Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
                .findFirst();

              if(serie.isPresent()) {

                  var serieEncontrada = serie.get();
                  List<DadosTemporadas> temporadas = new ArrayList<>();

                  for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                      var json = screenService.obterDados(ENDEREÇO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + APIKEY);
                      DadosTemporadas dadosTemporadas = converterDados.obterDados(json, DadosTemporadas.class);
                      temporadas.add(dadosTemporadas);

                  }

                  temporadas.forEach(System.out::println);

                   List<Episodio> episodios = temporadas.stream()
                            .flatMap(d -> d.dadosSerieList().stream()
                                    .map(e -> new Episodio(d.numero(),e)))
                                    .collect(Collectors.toList());

                  serieEncontrada.setEpisodio(episodios);
                  serieRepository.save(serieEncontrada);
              }else{
                  System.out.println("Serie não encontrada");
              }
    }
    public void listarSeriesBuscadas(){
            series = serieRepository.findAll();
            series.stream()
                    .sorted(Comparator.comparing(Serie::getGenero))
                    .forEach(System.out::println);
            opcao = 0;
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma série pelo nome: ");
        String nomeSerie = sc.nextLine();
        serieBusca = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if(serieBusca.isPresent()){
            System.out.println("Dados da série: " + serieBusca.get());
        }
        else {
            System.out.println("Série não encontrada");
        }

    }

    private void buscarSeriePorAtor() {
        sc.nextLine();
        System.out.println("Qual o nome para busca?");
        var nomeAtor = sc.nextLine();
        System.out.println("Avaliações a partir de que valor? ");
        var avaliacao = sc.nextDouble();
        List<Serie> seriesEncontradas = serieRepository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Séries em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));
    }

    private void topCincoSeries() {
        List<Serie> serieTop = serieRepository.findTop5ByOrderByAvaliacaoDesc();
        serieTop.forEach(s -> System.out.println(s.getTitulo() + " Avaliação " + s.getAvaliacao()));

    }

    private void buscarPorGenero() {

        System.out.println("Insira um gênero de série: ");
        String nomeGenero = sc.next();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Serie> seriesPorCategoria = serieRepository.findByGenero(categoria);
        System.out.println("Séries da categoria " + nomeGenero);
        seriesPorCategoria.forEach(System.out::println);

    }


}


