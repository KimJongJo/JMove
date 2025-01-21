package com.example.JMove.Controller;

import com.example.JMove.DTO.AuthRequest;
import com.example.JMove.Service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody Map<String, String> body){

        String email = body.get("email");
        String type = body.get("type");

        Map<String, Object> response = new HashMap<>();
        // type = (signup - 회원가입), (findId - 아이디찾기)
        switch (type){
            case "signup" : emailService.signup(email);
                response.put("message", "회원가입 이메일이 성공적으로 전송되었습니다.");
            break;
            case "findId" : emailService.findId(email);
                response.put("message", "아이디 찾기 이메일이 성공적으로 전송되었습니다.");
            break;
            default:
                response.put("message", "알 수 없는 오류,.,");
        }

        return ResponseEntity.ok(response);

    }

    @PostMapping("/auth")
    public ResponseEntity<Map<String, Object>> authCheck(@RequestBody AuthRequest request) {

        Map<String, Object> response = new HashMap<>();

        boolean authCheck = emailService.authCheck(request);

        if(authCheck){
            response.put("code", 1);
        }else{
            response.put("code", 0);
        }

        return ResponseEntity.ok(response);
    }

}
