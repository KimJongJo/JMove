package com.example.JMove.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {

    private String userId;
    private List<FavoriteDTO> favorites;

    // 생성자, getter, setter
    public UserDTO(String userId, List<FavoriteDTO> favorites) {
        this.userId = userId;
        this.favorites = favorites;
    }

}
