package com.example.JMove.Service;

import com.example.JMove.DAO.Favorite;
import com.example.JMove.Repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public List<Favorite> findByUserId(String userId){
        return favoriteRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteMovie(Map<String, Object> detailMovie, String userId) {

        Object movieIdObject = detailMovie.get("movieId"); // Object로 가져오기
        Long movieId = (movieIdObject instanceof Integer) ? Long.valueOf((Integer) movieIdObject) : (Long) movieIdObject;


        favoriteRepository.deleteByMovieIdAndUserId(movieId, userId);


    }
}
