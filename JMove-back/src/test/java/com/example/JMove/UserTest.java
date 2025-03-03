package com.example.JMove;

import com.example.JMove.Repository.UserRepository;
import com.example.JMove.Service.SignupService;
import com.example.JMove.Service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void textCheckId() {
        String id = "testId";
        boolean result = userService.existsByUserId(id);
        Assertions.assertThat(result).isFalse();
    }

}
