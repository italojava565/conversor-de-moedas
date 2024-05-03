package models;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Conversor {
    public Moeda converterMoeda(String valor1, String valor2, double moeda){
        String minhaKey = "6068198a6cf76eaaabd8d40d";
        URI endereco = URI.create("https://v6.exchangerate-api.com/v6/"+
                minhaKey+"/pair/"+valor1+"/"+valor2+"?amount="+moeda);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(endereco)
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Não consegui converter os valores");
        }

        return new Gson().fromJson(response.body(),Moeda.class);

    }
}
