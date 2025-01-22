package com.example.JMove.Service;

import com.example.JMove.DAO.User;
import com.example.JMove.DTO.updatePwRequest;
import com.example.JMove.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;


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
}
