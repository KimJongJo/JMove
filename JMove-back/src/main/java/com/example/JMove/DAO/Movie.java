package com.example.JMove.DAO;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "movies")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movie {

    @Id
    @Column(name = "movie_id", nullable = false, unique = true)
    private Long movieId;

    @Column(name = "movie_title")
    private String title;

    @Column(name = "movie_text")
    private String text;

    @Column(name = "movie_poster_path")
    private String posterPath;

    @Column(name = "movie_backdrop_path")
    private String backPosterPath;

    @Column(name = "average")
    private double average;

    @Column(name = "count")
    private int count;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites;


}
