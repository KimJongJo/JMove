package com.example.JMove.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignupRequest {

    private String id;
    private String email;
    private String password;
}
