package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    //Oauth2 로그인 아닌 회원 email로 찾기
    public List<Member> getUserProfiles(Long mno);

}
