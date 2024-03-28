package com.dot.tour_info_service_server.mapper;

import com.dot.tour_info_service_server.dto.request.auth.SignupRequestDTO;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.entity.Role;
import jakarta.annotation.Nullable;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    //역할 등록
    int roleSet(Long mno, String role);

    //이메일로 유저 검색(Oauth2 로그인 O or X)
    Member findMemberFromEmail(String email,boolean social);

    //유저 이름,전화번호로 유저 검색
    Member searchUserFromNameAndPhone(String name,String phone);

    //회원 검색(name,mno)
//    List<Member> searchMember(String name, Long mno);

//    //팔로워 수 조회
//    Long searchFollowers(Long mno);
//
//    //팔로잉 수 조회
//    Long searchFollowings(Long mno);

    //mno로 유저 정보 조회
    List<Member> getUserProfiles(Long mno);

    //회원가입 대기 조회
    Member[] signupWaits();

    //회원가입 승인
    int agreeSign(Long mno);

    //찜목록 수 조회
//    Long CartListNum(Long mno);

    //회원 조회 - 관리자
    Member[] memberSearchForAdmin(String filter, String name);

}
