package com.example.JMove.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FavoriteDTO {

    private Long favoriteId;
    private MovieDTO movie;  // Movie 정보만 포함
    private LocalDateTime addedAt;

    // 생성자, getter, setter
    public FavoriteDTO(Long favoriteId, MovieDTO movie, LocalDateTime addedAt) {
        this.favoriteId = favoriteId;
        this.movie = movie;
        this.addedAt = addedAt;
    }
}
