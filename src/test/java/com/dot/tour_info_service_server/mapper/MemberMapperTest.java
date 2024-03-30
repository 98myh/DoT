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

    //이메일로 유저 검색(Oauth2 로그인 O or X)
    @Test
    public void findEmailTest(){
        log.info("이메일 유저 검색 테스트 1 : "+memberMapper.findMemberFromEmail("fldh3369@naver.com",false));
        log.info("이메일 유저 검색 테스트 2 : "+memberMapper.findMemberFromEmail("fldh3369@naver.com",true));
    }

    //유저 이름, 전화번호로 유저 검색 테스트
    @Test
    public void searchUserTest(){
        log.info("이름, 전화번호로 검색 1: "+memberMapper.searchUserFromNameAndPhone("문영현","01012345678"));
        log.info("이름, 전화번호로 검색 2: "+memberMapper.searchUserFromNameAndPhone("test","010-1234-5678"));
    }

    //mno 로 유저 정보 조회 테스트
    @Test
    public void getUserProfilesTest(){
        log.info("mno로 유저 정보 조회 테스트 1 :"+memberMapper.getUserProfiles(1l));
        log.info("mno로 유저 정보 조회 테스트 2 :"+memberMapper.getUserProfiles(2l));
    }

    //회원가입 대기 조회
    @Test
    public void signupWaiteTest(){
        log.info("회원가입 대기 조회  : "+memberMapper.signupWaits());
    }

    //회원가입 승인 테스트
    @Test
    public void agreeSignTest(){
        log.info("회원가입 승인 테스트 : "+memberMapper.agreeSign(1l));
    }

    //회원 조회 - 관리자
    @Test
    public void memberSearchTest(){
        log.info("회원 조회(관리자) 테스트 1: "+memberMapper.memberSearchForAdmin("","t"));
        log.info("회원 조회(관리자) 테스트 2: "+memberMapper.memberSearchForAdmin("MEMBER","t"));
        log.info("회원 조회(관리자) 테스트 3: "+memberMapper.memberSearchForAdmin("ADMIN","t"));
    }

    //비밀번호 변경 테스트
    @Test
    public void passwordChangeTest(){
        log.info("비밀번호 변경 테스트 : "+memberMapper.changePassword(1l,"0000",false));
    }

    //이메일 인증 완료 테스트
    @Test
    public void emailValidateTest(){
        log.info("이메일 인증 테스트 " +memberMapper.emailValidate("fldh3369@naver.com"));
    }


}