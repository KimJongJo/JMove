package com.example.JMove.Controller;

import com.example.JMove.DAO.User;
import com.example.JMove.DTO.LoginRequest;
import com.example.JMove.DTO.updatePwRequest;
import com.example.JMove.Repository.UserRepository;
import com.example.JMove.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        
        String token = userService.login(request);

        if(token == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("fail login");
        }

        return ResponseEntity.ok().body(Map.of("token", token));

    }


}
