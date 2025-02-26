package com.example.JMove.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTO {
    private Long movieId;
    private String title;
    private String text;
    private String posterPath;
    private String backPosterPath;
    private double average;
    private int count;

    // 생성자, getter, setter
    public MovieDTO(Long movieId, String title, String text, String posterPath, String backPosterPath, double average, int count) {
        this.movieId = movieId;
        this.title = title;
        this.text = text;
        this.posterPath = posterPath;
        this.backPosterPath = backPosterPath;
        this.average = average;
        this.count = count;
    }
}
