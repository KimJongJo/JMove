package com.example.JMove.Repository;

import com.example.JMove.DAO.Favorite;
import com.example.JMove.DAO.Movie;
import com.example.JMove.DAO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByUserAndMovie(User user, Movie movie);
    List<Favorite> findByUser(Optional<User> user);

    void deleteByMovieAndUser(Movie movie, User user);
}
