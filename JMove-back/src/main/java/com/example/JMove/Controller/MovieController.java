package com.example.JMove.Controller;

import com.example.JMove.Config.jwt.TokenProvider;
import com.example.JMove.Service.MovieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class MovieController {

    private final TokenProvider tokenProvider;
    private final MovieService movieService;

    @PostMapping("/movies")
    public ResponseEntity<?> addMovie(@RequestBody Map<String, Object> movie, HttpServletRequest request){

        String token = extractTokenFromCookies(request);

        if(token == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 없습니다.");
        }

        // 토큰 검증
        boolean isTokenValid = movieService.isTokenValid(token);
        if(!isTokenValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 토큰입니다.");
        }

        String userId = tokenProvider.getUserId(token);


        // 선택한 영화가 movie db에 있는지 확인 => 영화 저장
        movieService.checkMovie(movie, userId);


        return ResponseEntity.ok("영화가 저장되었습니다.");


    }

    // jwt 토큰 꺼내기
    private String extractTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
