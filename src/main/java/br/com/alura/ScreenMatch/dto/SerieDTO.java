package br.com.alura.ScreenMatch.dto;

import br.com.alura.ScreenMatch.entities.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SerieDTO(Long id,String titulo, Integer totalTemporadas,Double avaliacao,
                       Categoria genero,String poster,String sinopse, String atores) {
}
