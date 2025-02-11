package com.example.JMove.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@RestController
public class MainController {

    @Value("${TMDBapi.token}")
    private String token;

    @Value("${TMDBapi.key}")
    private String apiKey;

    // 추천하는 영화 목록
    @GetMapping(value = "posters", produces = "application/json; charset=UTF-8")
    public String postAPI(){

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/movie/popular?language=ko&include_adult=false&page=1&certification_country=KR&certification.lte=18&week?api_key=" + apiKey ))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try{
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 현재 상영중인 영화 목록
    @GetMapping(value = "movies", produces = "application/json; charset=UTF-8")
    public String movies(@RequestParam("page") int page ){

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/movie/popular?language=ko&page=" + page + 1 + "&certification_country=KR&certification.lte=18&include_adult=false&week?api_key=" + apiKey))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try{
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    // 영화 검색 목록
    @GetMapping(value = "/movies/search", produces = "application/json; charset=UTF-8")
    public String searchMovies(@RequestParam("q") String searchMovieName){

        System.out.println("검색어 = " + searchMovieName);


        try{
            String encodedSearchMovieName = URLEncoder.encode(searchMovieName, StandardCharsets.UTF_8.toString());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.themoviedb.org/3/search/movie?query=" + encodedSearchMovieName + "&include_adult=false&language=ko&page=1&api_key=" + apiKey))
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();


            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
