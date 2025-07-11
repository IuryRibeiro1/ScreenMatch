package br.com.alura.ScreenMatch;

import br.com.alura.ScreenMatch.entities.ScreenDados;
import br.com.alura.ScreenMatch.programa.Principal;
import br.com.alura.ScreenMatch.repository.SerieRepository;
import br.com.alura.ScreenMatch.service.ConverterDados;
import br.com.alura.ScreenMatch.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jackson.JsonComponent;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {
	@Autowired
	SerieRepository serieRepository;

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(serieRepository);
		principal.exibeMenu();


	}
}
