package com.dot.tour_info_service_server.mapper;

import com.dot.tour_info_service_server.dto.request.auth.SignupRequestDTO;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.mapper.MemberMapper;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootTest
@Transactional
// 실 데이터베이스에 연결 시 필요한 어노테이션
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Log4j2
class MemberMapperTest {
    @Autowired
    private MemberMapper memberMapper;

    //로그인 테스트
    @Test
    public void loginTest(){
        log.info("로그인 테스트 : "+memberMapper.getLogin("fldh3369@naver.com"));
    }
    @Test
    public void findMembertest(){
        log.info("테스트 : "+memberMapper.getUserProfiles(1l));

    }

    //이메일 중복 테스트
    @Test
    public void existEmailTest(){
        log.info("중복 테스트1 : "+memberMapper.existEmail("fldh3369@naver.com"));
        log.info("중복 테스트2 : "+memberMapper.existEmail("fldh33691@naver.com"));
    }

    //회원가입 테스트
    @Test
    public void  signupTest(){
        Member member=Member.builder()
                .email("test")
                .password("1234")
                .birth(LocalDate.now())
                .businessId(null)
                .name("테스트")
                .phone("010-1234-5678")
                .build();
        Long mno=memberMapper.signUp(member);
        log.info("회원가입 테스트 : "+memberMapper.signUp(member));
        log.info("롤 테스트 : "+memberMapper.roleSet(mno,"MEMBER"));
    }


}