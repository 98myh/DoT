package com.dot.tour_info_service_server.mapper;

import com.dot.tour_info_service_server.dto.request.auth.SignupRequestDTO;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface MemberMapper {

    //로그인하기 위한 유저 정보 들고오기
    Member getLogin(String email);

    //이메일 중복 확인
    int existEmail(String email);

    //회원가입
    Long signUp(Member member);

    int roleSet(Long mno, String role);

    //Oauth2 로그인 아닌 회원 email로 찾기
    List<Member> getUserProfiles(Long mno);

}
