package com.example.JMove.Service;

import com.example.JMove.Config.jwt.TokenProvider;
import com.example.JMove.DAO.Favorite;
import com.example.JMove.DAO.Movie;
import com.example.JMove.Repository.FavoriteRepository;
import com.example.JMove.Repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final TokenProvider tokenProvider;
    private final MovieRepository movieRepository;
    private final FavoriteRepository favoriteRepository;


    // 토큰 검증하기
    public boolean isTokenValid(String token) {

        return tokenProvider.validToken(token);
    }

    // 가져온 영화가 movie db에 저장되어 있나 확인
    public boolean checkMovie(Map<String, Object> movieMap, String userId) {

        Map<String, Object> detailMovie = (Map<String, Object>) movieMap.get("movie");
        Integer movieIdInteger = (Integer) detailMovie.get("id");
        Long movieId = Long.valueOf(movieIdInteger.longValue());


        //Long movieId = Long.parseLong((String) detailMovie.get("id"));
        Optional<Movie> checkMovie = movieRepository.findById(movieId);

        //가져온 영화가 db에 없으면 저장
        if(checkMovie.isEmpty()){

            Movie movie = Movie.builder()
                    .movieId(movieId)
                    .title((String)detailMovie.get("title"))
                    .text((String)detailMovie.get("overview"))
                    .posterPath((String)detailMovie.get("poster_path"))
                    .backPosterPath((String)detailMovie.get("backdrop_path"))
                    .average(((Number) detailMovie.get("vote_average")).doubleValue())  // 수정된 부분
                    .count((int)detailMovie.get("vote_count"))
                    .build();


            // db에 저장
            movieRepository.save(movie);

            addMovie(movieId, userId);

        }else{
            addMovie(checkMovie.get().getMovieId(), userId);
        }

        return true;

    }

    // 영화를 저장 => favorite
    public void addMovie(Long movieId, String userId){

        // 이미 마이페이지에 저장되어 있으면 넘어감
        boolean checkFavorite = favoriteRepository.existsByUserIdAndMovieId(userId, movieId);

        if(!checkFavorite){
            LocalDateTime date = LocalDateTime.now();

            Favorite favorite = Favorite.builder()
                    .userId(userId)
                    .movieId(movieId)
                    .addedAt(date)
                    .build();

            favoriteRepository.save(favorite);
        }
    }

    public List<Movie> findByIdIn(List<Long> movieIds) {
        return movieRepository.findByMovieIdIn(movieIds);
    }
}
