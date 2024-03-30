package com.dot.tour_info_service_server.service.auth;

import com.dot.tour_info_service_server.dto.request.auth.ChangePasswordRequestDTO;
import com.dot.tour_info_service_server.dto.request.auth.EmailRequestDTO;
import com.dot.tour_info_service_server.dto.request.auth.LoginRequestDTO;
import com.dot.tour_info_service_server.dto.request.auth.SignupRequestDTO;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Log4j2
class AuthServiceImplTest {

    @Autowired
    private AuthService authService;

    //로그인 테스트
    @Test
    public void LoginTest() throws Exception{
        log.info("로그인 서비스 테스트1 : "+authService.login(LoginRequestDTO.builder()
                        .email("fldh3369@naver.com")
                        .password("1234")
                .build()));
        log.info("로그인 테스트2 : "+authService.login(LoginRequestDTO.builder()
                        .email("fldh3369@naver.com")
                        .password("0000")
                .build()));
    }

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
    
    //이메일 확인 테스트
    @Test
    public void emailCheckTest(){
        log.info("이메일 확인 서비스 테스트1 : "+authService.emailCheck("fldh3369@naver.com"));
        log.info("이메일 확인 서비스 테스트2 : "+authService.emailCheck("fldh3369@naver.com123"));
    }

    //이메일 찾기 테스트
    @Test
    public void findEmailTest() throws Exception {
        log.info("이메일 찾기 서비스 테스트1 :"+authService.findEmail("test","010-1234-5678"));
        log.info("이메일 찾기 서비스 테스트2 :"+authService.findEmail("test","01012345678"));
    }

    //비밀번호 변경 테스트
    @Test
    public void changePasswordTest() throws AccountNotFoundException{
        log.info("비밀번호 변경 서비스 테스트1 : "+authService.changePassword(ChangePasswordRequestDTO.builder()
                        .email("fldh3369@naver.com")
                        .oldPassword("1234")
                        .newPassword("0000")
                .build()));

        log.info("비밀번호 변경 서비스 테스트2 : "+authService.changePassword(ChangePasswordRequestDTO.builder()
                .email("fldh3369@naver.com")
                .oldPassword("2222")
                .newPassword("1234")
                .build()));
    }

    //비밀번호 초기화 테스트
    @Test
    public void resetPasswordTest(){
        log.info("비밀번호 초기화 서비스 테스트1 :"+authService.resetPassword(EmailRequestDTO.builder()
                        .email("fldh3369@naver.com")
                .build()));

        log.info("비밀번호 초기화 서비스 테스트2 :"+authService.resetPassword(EmailRequestDTO.builder()
                .email("fldh336912@naver.com")
                .build()));
    }

    //이메일 인증 테스트
    @Test
    public void chaeckValidateTest(){
        log.info("이메일 인증 확인 서비스 테스트1 : "+authService.checkValidate("fldh3369@naver.com"));
        log.info("이메일 인증 확인 서비스 테스트2 : "+authService.checkValidate("fldh336912@naver.com"));
    }

    //이메일 재전송 테스트
    @Test
    public void resendEmailTest() throws Exception{
        authService.resendEmail("fldh3369@naver.com");
        authService.resendEmail("fldh336912@naver.com");
    }

}