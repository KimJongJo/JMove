package com.example.JMove.Service;

import com.example.JMove.DAO.Auth;
import com.example.JMove.DAO.User;
import com.example.JMove.DTO.AuthRequest;
import com.example.JMove.DTO.MailDTO;
import com.example.JMove.Repository.AuthRepository;
import com.example.JMove.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender mailSender;

    private final AuthRepository authRepository;

    private final UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String username;

    // 회원가입 이메일
    public void signup(String email) {

        String random = random();

        MailDTO mailDTO = MailDTO.builder()
                .address(email)
                .title("회원가입 이메일 인증")
                .message("이메일 인증 문자 입니다. 인증번호란에 입력하신 뒤 인증을 눌러주세요 \n" + random)
                .build();

        // 인증 테이블에 저장
        authEmail(email, random);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(mailDTO.getAddress());
        simpleMailMessage.setSubject(mailDTO.getTitle());
        simpleMailMessage.setText(mailDTO.getMessage());

        mailSender.send(simpleMailMessage);


        
    }
    
    // 아이디 찾기 이메일
    public void findId(String email){

        User user = userRepository.findByEmail(email);

        MailDTO mailDTO = MailDTO.builder()
                .address(email)
                .title("JMove 아이디 찾기")
                .message("회원의 아이디는 \n" + user.getId() + "\n입니다.")
                .build();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(mailDTO.getAddress());
        simpleMailMessage.setSubject(mailDTO.getTitle());
        simpleMailMessage.setText(mailDTO.getMessage());

        mailSender.send(simpleMailMessage);
        
    }

    // 무작위 문자 6자리 만들기
    public String random() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int z = (int)(Math.random() * 10);

            // 5보다 크면 문자
            if (z > 5) {
                // 대문자 또는 소문자 문자 랜덤 생성
                char ch = (char)((Math.random() * 26) + 'a'); // 소문자 'a'부터 'z'까지
                sb.append(ch);
            } else { // 작으면 숫자
                int num = (int)(Math.random() * 10); // 0 ~ 9 사이의 숫자
                sb.append(num);
            }
        }

        return sb.toString();
    }

    // 인증 테이블에 저장
    @Transactional
    public void authEmail(String email, String random){

        Optional<Auth> authOptional = authRepository.findById(email);

        System.out.println(email + random);

        // 인증 테이블에 존재하면 인증번호를 바꿔주기
        if(authOptional.isPresent()){

            Auth auth = authOptional.get();
            auth.setAuthNum(random);

            authRepository.save(auth);

        }else { // 아니라면 인증 테이블에 추가
            Auth auth = Auth.builder()
                    .email(email)
                    .authNum(random)
                    .build();

            authRepository.save(auth);

        }

    }

    // 인증 번호 확인
    public boolean authCheck(AuthRequest request) {
        return authRepository.findById(request.getEmail())
                .map(auth -> {
                    return auth.getAuthNum().equals(request.getAuthNum());
                })
                .orElse(false);
    }
}
