package com.example.JMove.DAO;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "FAVORITE")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorite_seq")  // 시퀀스를 사용하여 자동 증가
    @SequenceGenerator(name = "favorite_seq", sequenceName = "favorite_seq", allocationSize = 1)
    @Column(name = "favorite_id")
    private Long favoriteId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "movie_id", nullable = false)
    private Long movieId;

    @CreationTimestamp  // 객체 생성 시 현재 시간 자동 삽입
    @Column(name = "added_at")
    private LocalDateTime addedAt;
}
