package com.example.JMove.Repository;

import com.example.JMove.DAO.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByUserIdAndMovieId(String userId, Long movieId);
    List<Favorite> findByUserId(String userId);

    void deleteByMovieIdAndUserId(Long movieId, String userId);
}
