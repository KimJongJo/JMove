package com.example.JMove.Service;

import com.example.JMove.DAO.Favorite;
import com.example.JMove.DAO.Movie;
import com.example.JMove.DAO.User;
import com.example.JMove.Repository.FavoriteRepository;
import com.example.JMove.Repository.MovieRepository;
import com.example.JMove.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public List<Favorite> findByUserId(User user){
        return favoriteRepository.findByUser(Optional.ofNullable(user));
    }

    @Transactional
    public void deleteMovie(Map<String, Object> detailMovie, String userId) {

        Object movieIdObject = detailMovie.get("movieId"); // Object로 가져오기
        Long movieId = (movieIdObject instanceof Integer) ? Long.valueOf((Integer) movieIdObject) : (Long) movieIdObject;

        Optional<User> user = userRepository.findById(userId);
        Optional<Movie> movie = movieRepository.findById(movieId);


        favoriteRepository.deleteByMovieAndUser(movie.get(), user.get());


    }

    public List<Favorite> findByUser(User user) {
        return favoriteRepository.findByUser(Optional.ofNullable(user));
    }
}
