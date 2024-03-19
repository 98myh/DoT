package com.dot.tour_info_service_server.mapper;

import com.dot.tour_info_service_server.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    //로그인하기 위한 유저 정보 들고오기
    Member getLogin(String email);

    //Oauth2 로그인 아닌 회원 email로 찾기
    List<Member> getUserProfiles(Long mno);

}
