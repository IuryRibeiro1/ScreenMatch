package br.com.alura.ScreenMatch.service;

public interface IConverterDados {
    <T> T obterDados(String json, Class<T> classe);
}
