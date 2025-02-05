package com.example.JMove.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private static final String KAKAO_AUTH_URL = "https://kauth.kakao.com/oauth/authorize";
    private static final String NAVER_AUTH_URL = "https://nid.naver.com/oauth2.0/authorize";
    private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";

    @Value("${login.redirect.uri}")
    private String REDIRECT_URI;

    @Value("${login.api.client.id.kakao}")
    private String CLIENT_ID_KAKAO;

    @Value("${login.api.client.id.naver}")
    private String CLIENT_ID_NAVER;

    @Value("${login.api.client.id.google}")
    private String CLIENT_ID_GOOGLE;

    @PostMapping("/login")
    public ResponseEntity<?> loginAPI(@RequestParam ("code") int code){
        String provider = getProvider(code);
        if (provider == null) {
            return ResponseEntity.badRequest().body("잘못된 로그인 코드");
        }

        String authUrl = getAuthUrl(provider);
        return ResponseEntity.ok(Map.of("redirectUrl", authUrl));
    }

    private String getProvider(int code) {
        return switch (code) {
            case 1 -> "kakao";
            case 2 -> "naver";
            case 3 -> "google";
            default -> null;
        };
    }

    private String getAuthUrl(String provider) {
        return switch (provider) {
            case "kakao" -> KAKAO_AUTH_URL + "?client_id=" + CLIENT_ID_KAKAO + "&redirect_uri=" + REDIRECT_URI + "&response_type=code&state=" + provider;
            case "naver" -> NAVER_AUTH_URL + "?client_id=" + CLIENT_ID_NAVER + "&redirect_uri=" + REDIRECT_URI + "&response_type=code&state=" + provider;
            case "google" -> GOOGLE_AUTH_URL + "?client_id=" + CLIENT_ID_GOOGLE + "&redirect_uri=" + REDIRECT_URI + "&response_type=code&scope=email profile&state=" + provider;
            default -> throw new IllegalArgumentException("Invalid provider");
        };
    }






}
