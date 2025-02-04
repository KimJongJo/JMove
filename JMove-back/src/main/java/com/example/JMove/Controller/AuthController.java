package com.example.JMove.Controller;

import com.example.JMove.Config.jwt.TokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenProvider tokenProvider;

    // 로그인 상태 확인 api
    @GetMapping("/check")
    public boolean checkLoginStatus(HttpServletRequest request) {

        String token = extractTokenFromRequest(request);

        if(token != null && tokenProvider.validToken(token)){
            // jwt 토큰이 유효하면 로그인된 상태
            return true;
        }

        // 토큰이 없거나 유효하지 않으면 로그인 되지 않은 상태
        return false;
    }

    // HTTP 요청에서 jwt 토큰을 추출하는 메서드
    private String extractTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Bearer 이후 토큰 반환
        }

        return getTokenFromCookies(request);
    }

    // 쿠키에서 jwt 토큰을 추출하는 메서드
    private String getTokenFromCookies(HttpServletRequest request) {
        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;

    }
}
