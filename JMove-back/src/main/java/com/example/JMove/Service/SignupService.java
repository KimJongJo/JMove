package com.example.JMove.Service;

import com.example.JMove.DAO.User;
import com.example.JMove.DTO.SignupRequest;
import com.example.JMove.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public boolean signup(SignupRequest signupRequest) {
        signupRequest.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));

        User user = User.builder()
                .id(signupRequest.getId())
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword())
                .createAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return true;
    }
}
