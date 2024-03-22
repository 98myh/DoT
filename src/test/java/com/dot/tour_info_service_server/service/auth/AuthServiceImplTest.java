package com.dot.tour_info_service_server.service.auth;

import com.dot.tour_info_service_server.dto.request.auth.SignupRequestDTO;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Log4j2
class AuthServiceImplTest {

    @Autowired
    private AuthService authService;

    //회원가입 테스트
    @Test
    public void SignupTest() throws Exception {
        log.info("회원가입 Service 테스트 : " +authService.signup(SignupRequestDTO.builder()
                .email("test@naver.com")
                .password("1234")
                .birth(LocalDate.now())
                .businessId(null)
                .name("테스트")
                .phone("010-1234-5678")
                .build()));
    }

}