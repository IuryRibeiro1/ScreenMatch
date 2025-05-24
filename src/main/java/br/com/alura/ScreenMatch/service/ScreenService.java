package br.com.alura.ScreenMatch.service;

import br.com.alura.ScreenMatch.entities.ScreenDados;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ScreenService {


        public String getDados(String dados){

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://www.omdbapi.com/?t=Matrix&apikey=a64d765c")) .build();
            HttpResponse<String> response = null;

        try{
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        }catch(IOException e){
            throw new RuntimeException();
        }catch (InterruptedException e){
            throw new RuntimeException();
        }
            String json = response.body();
        return json;
        }

}
