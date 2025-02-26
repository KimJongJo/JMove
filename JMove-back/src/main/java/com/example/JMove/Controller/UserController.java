package com.example.JMove.Controller;

import com.example.JMove.Config.jwt.TokenProvider;
import com.example.JMove.DAO.Favorite;
import com.example.JMove.DAO.Movie;
import com.example.JMove.DAO.User;
import com.example.JMove.DTO.*;
import com.example.JMove.Service.FavoriteService;
import com.example.JMove.Service.MovieService;
import com.example.JMove.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Getter
    @AllArgsConstructor
    static class Response {
        private int code;
        private String message;
    }

    private final UserService userService;
    private final FavoriteService favoriteService;
    private final MovieService movieService;
    private final TokenProvider tokenProvider;


    @GetMapping("/check-id")
    public ResponseEntity<Response> checkId(@RequestParam("id") String id){

        boolean isAvailable = !userService.existsByUserId(id);

        return ResponseEntity.ok(
                new Response(
                        isAvailable ? 1 : 0,
                        isAvailable ? "사용 가능한 아이디" : "이미 사용중인 아이디"
                )
        );
    }

    // 이메일 찾기
    @GetMapping("/check-email")
    public ResponseEntity<Response> checkEmail(@RequestParam ("email") String email){

        boolean isAvailable = userService.existsByUserEmail(email);

        return ResponseEntity.ok(
                new Response(
                        isAvailable ? 1 : 0,
                        isAvailable ? "아이디 찾기 가능한 이메일" : "아이디 찾기 불가능한 이메일"
                )
        );

    }

    // 아이디와 이메일을 받아서 존재하는 회원인지 확인
    @PostMapping("/checkIdEmail")
    public ResponseEntity<Response> checkIdEmail(@RequestBody Map<String, String> body){

        String id = body.get("id");
        String email = body.get("email");

        boolean isAvailable = userService.existsByUserIdAndEmail(id, email);

        return ResponseEntity.ok(
                new Response(
                        isAvailable ? 1 : 0,
                        isAvailable ? "비밀번호 찾기 활성화" : "존재하는 회원이 아님"
                )
        );
        

    }

    // 아이디, 이메일, 비밀번호를 받아 비밀번호 변경
    @PatchMapping("/update")
    public ResponseEntity<Response> updatePw(@RequestBody updatePwRequest request){

        boolean updateSuccess = userService.updatePw(request);

        return ResponseEntity.ok(
                new Response(
                        updateSuccess ? 1 : 0,
                        updateSuccess ? "비밀번호 변경 성공" : "비밀번호 변경 실패"
                )
        );

    }

    // 로그인
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response){

        System.out.println(request);

        String token = userService.login(request);

        if(token == null){

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("fail login");
        }



        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true); // 자바스크립트에서 접근 불가
        cookie.setSecure(true); // HTTPS에서만 전송 -> 배포시 필요
        cookie.setPath("/"); // 모든 경로에서 쿠키 사용가능
        cookie.setMaxAge(3600); // 1시간동안 유지
        response.addCookie(cookie);

        return ResponseEntity.ok("로그인 성공");

    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletRequest request, HttpServletResponse response){

        // jwt 토큰을 담고 있는 쿠키 삭제
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS 환경에서만
        cookie.setPath("/"); // 모든 경로에서 쿠키 삭제
        cookie.setMaxAge(0); // 쿠키 만료
        response.addCookie(cookie);

        return ResponseEntity.ok("로그아웃 성공");

    }

    @GetMapping("/mypage")
    public ResponseEntity<UserDTO> mypage(HttpServletRequest request) {
        String token = extractTokenFromCookies(request);
        String userId = tokenProvider.getUserId(token);

        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            List<Favorite> favoriteList = favoriteService.findByUser(user.get());

            // Favorite -> FavoriteDTO, Movie -> MovieDTO 변환
            List<FavoriteDTO> favoriteDtos = favoriteList.stream()
                    .map(fav -> new FavoriteDTO(
                            fav.getFavoriteId(),
                            new MovieDTO(
                                    fav.getMovie().getMovieId(),  // 영화 ID
                                    fav.getMovie().getTitle(),  // 영화 제목
                                    fav.getMovie().getText(),  // 영화 설명
                                    fav.getMovie().getPosterPath(),  // 포스터 경로
                                    fav.getMovie().getBackPosterPath(),  // 백포스터 경로
                                    fav.getMovie().getAverage(),  // 영화 평점
                                    fav.getMovie().getCount()  // 리뷰 수
                            ),
                            fav.getAddedAt()  // 추가된 시간
                    ))
                    .collect(Collectors.toList());

            UserDTO userDto = new UserDTO(userId, favoriteDtos);

            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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


    // 마이페이지 영화 삭제
    @PostMapping("/movies")
    public ResponseEntity<?> removeMovie(@RequestBody Map<String, Object> movie, HttpServletRequest request){

        String token = extractTokenFromCookies(request);
        String userId = tokenProvider.getUserId(token);


        Map<String, Object> detailMovie = (Map<String, Object>) movie.get("movie");
        favoriteService.deleteMovie(detailMovie, userId);

        
        return ResponseEntity.ok("삭제 완료");
    }



}
