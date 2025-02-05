package com.example.JMove.Service;

import com.example.JMove.Config.jwt.TokenProvider;
import com.example.JMove.Controller.UserController;
import com.example.JMove.DAO.User;
import com.example.JMove.DTO.LoginRequest;
import com.example.JMove.DTO.updatePwRequest;
import com.example.JMove.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;


    // 아이디로 존재하는 회원인지 확인
    public boolean existsByUserId(String id) {

        return userRepository.existsById(id);
    }

    // 이메일로 존재하는 회원인지 확인
    public boolean existsByUserEmail(String email) {

        return userRepository.existsByEmail(email);
    }


    // 아이디, 이메일로 존재하는 회원인지 확인
    public boolean existsByUserIdAndEmail(String id, String email) {

        return userRepository.existsByIdAndEmail(id, email);
    }


    // 비밀번호 변경
    @Transactional
    public boolean updatePw(updatePwRequest request) {

        String id = request.getId();
        String email = request.getEmail();
        String password = request.getPw();

        User user = userRepository.findByIdAndEmail(id, email);

        // 변경하려는 비밀번호가 현재 비밀번호와 같을 때
        if(bCryptPasswordEncoder.matches(password, user.getPassword())){
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(user);

        return true;

    }

    // 일반 로그인
    public String login(LoginRequest request){

        String id = request.getId();
        String pw = request.getPw();

        // 1. 입력한 아이디가 db에 있는지 확인
        Optional<User> user = userRepository.findById(id);

        // 2. 없다면 false 리턴, 있으면 암호화된 비밀번호와 입력한 비밀번호가 같은지 확인
        if (user.isEmpty()){
            return null;
        }else{
            // 3. 같으면 로그인 가능, 틀리면 로그인 불가능
            if(bCryptPasswordEncoder.matches(pw, user.get().getPassword())){

                String token = tokenProvider.generateToken(user.get(), Duration.ofHours(1));

                return token;
            }else{
                return null;
            }
        }
    }

    public String loginAPI(String userId) {
        // 1. 입력한 아이디가 db에 있는지 확인
        Optional<User> user = userRepository.findById(userId);

        // 2. 없다면 false 리턴, 있으면 암호화된 비밀번호와 입력한 비밀번호가 같은지 확인
        if (user.isEmpty()){
            return null;
        }else{
            String token = tokenProvider.generateToken(user.get(), Duration.ofHours(1));

            return token;
        }
    }
}
