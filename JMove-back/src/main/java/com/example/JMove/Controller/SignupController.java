package com.example.JMove.Controller;

import com.example.JMove.DAO.User;
import com.example.JMove.DTO.SignupRequest;
import com.example.JMove.Service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @GetMapping("/check-id")
    public ResponseEntity<Map<String, Object>> checkId(@RequestParam ("id") String id){

        boolean isAvailable = !signupService.existsByUserId(id);
        Map<String, Object> response = new HashMap<>();
        response.put("available", isAvailable);

        if(isAvailable){ // 사용 가능한 아이디면
            response.put("code", 1);
            response.put("message", "사용 가능한 아이디입니다.");
        }else { // 이미 존재하는 아이디면
            response.put("code", 0);
            response.put("message", "이미 사용중인 아이디입니다.");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> signup(@RequestBody SignupRequest signupRequest){


        System.out.println(signupRequest);
        // 비밀번호 암호화 해야함
        boolean signupSuccess = signupService.signup(signupRequest);

        Map<String, Object> response = new HashMap<>();
        if(signupSuccess) {
            response.put("code", 1);
            response.put("message", "회원가입이 완료되었습니다.");
        }else{
            response.put("code", 0);
            response.put("message", "회원가입에 실패했습니다.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
