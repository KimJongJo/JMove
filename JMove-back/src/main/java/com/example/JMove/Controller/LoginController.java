package com.example.JMove.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<?> loginAPI(@RequestParam ("code") int code){

        switch(code) {
            case 1 :
                System.out.println("kakaoLogin"); break;
            case 2 :
                System.out.println("naverLogin"); break;
            case 3 :
                System.out.println("googleLogin"); break;
        }


        return ResponseEntity.ok("로그인 성공");
    }





}
