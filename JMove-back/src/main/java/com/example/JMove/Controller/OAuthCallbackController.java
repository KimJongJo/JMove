package com.example.JMove.Controller;

import com.example.JMove.Config.jwt.TokenProvider;
import com.example.JMove.DAO.User;
import com.example.JMove.Service.OAuthService;
import com.example.JMove.Service.SignupService;
import com.example.JMove.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthCallbackController {

    private final OAuthService oAuthService;

    private final UserService userService;
    private final SignupService signupService;

    @GetMapping("/callback")
    public void callback(@RequestParam("code") String authCode, @RequestParam("state") String provider,
                                      HttpServletResponse response) throws IOException {


        if(!provider.equals("kakao") && !provider.equals("naver") && !provider.equals("google")){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 provider");
            return;
        }

        // Service로 넘겨서 액세스 토큰 요청
        String accessToken = oAuthService.getAccessToken(authCode, provider);

        // Service에서 사용자 아이디 가져오기
        // kakao = id가 바로 나옴
        // naver = response 안에 id가 나옴
        // google = sub이 바로 나옴 -> sub == id
        String userId = oAuthService.getUserInfo(accessToken, provider);

        // id를 확인해서 존재하는 회원인지 확인
        boolean user = userService.existsByUserId(userId);


        if(!user){ // 존재하지않는 회원이면 회원가입
            signupService.signup(userId);
        }

        // 로그인 처리
        String token = userService.loginAPI(userId);

        if(token == null){
            response.sendError(0, "jwt 토큰 생성 오류");
        }

        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true); // 자바스크립트에서 접근 불가
        cookie.setSecure(true); // HTTPS에서만 전송 -> 배포시 필요
        cookie.setPath("/"); // 모든 경로에서 쿠키 사용가능
        cookie.setMaxAge(3600); // 1시간동안 유지
        response.addCookie(cookie);
//
        response.sendRedirect("http://localhost:3000");

    }


}
