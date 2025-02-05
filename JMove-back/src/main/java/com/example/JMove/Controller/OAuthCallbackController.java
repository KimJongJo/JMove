package com.example.JMove.Controller;

import com.example.JMove.Service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthCallbackController {

    private final OAuthService oAuthService;

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String authCode, @RequestParam("state") String provider){


        if(!provider.equals("kakao") && !provider.equals("naver") && !provider.equals("google")){
            return ResponseEntity.badRequest().body("잘못된 provider");
        }

        // Service로 넘겨서 액세스 토큰 요청
        String accessToken = oAuthService.getAccessToken(authCode, provider);

        // Service에서 사용자 정보 가져오기
        Map<String, Object> userInfo = oAuthService.getUserInfo(accessToken, provider);

        System.out.println(userInfo);

        return null;

    }


}
