package com.example.JMove.DAO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Setter
@Getter
@Table(name = "auth")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auth {

    @Id
    @Column(name = "auth_email", nullable = false, unique = true)
    private String email;

    @Column(name = "auth_no")
    private String authNum;
}
