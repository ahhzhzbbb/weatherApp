package org.hoang.model;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


public class WeatherAPI {

    //call WeatherAPI
    public String callAPI(String city) throws IOException, InterruptedException {
        String apiKey = "8397dbecdc6cbd553ae3345a06b44a16";
        String encodeCity = URLEncoder.encode(city);
        //https://api.openweathermap.org/data/2.5/forecast?q=Hanoi&appid=8397dbecdc6cbd553ae3345a06b44a16&units=metric&cnt=32
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="
                + encodeCity + "&appid=" + apiKey + "&units=metric&cnt=32";

        // Tạo HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Tạo request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Gửi request và nhận response
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    //suggest city name in textField
    public String suggestName(String str) throws IOException, InterruptedException {
        String apiKey = "8397dbecdc6cbd553ae3345a06b44a16";
        String encodeCity = URLEncoder.encode(str, StandardCharsets.UTF_8);
        String url = "https://api.openweathermap.org/geo/1.0/direct?q="
                + encodeCity + "&limit=5&appid=" + apiKey;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
