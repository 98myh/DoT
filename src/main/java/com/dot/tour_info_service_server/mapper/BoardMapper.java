package com.dot.tour_info_service_server.mapper;

import com.dot.tour_info_service_server.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    //장소에 대한 게시글 정보들 조회
    List<Board> boardAboutPlaceInfo(Long pno);

    //해당 mno의 게시글 mno null로 수정
    int setBoardMnoNull(Long mno);

    // 장소,코스 포스팅 정보 조회 board, place, image, boardLike repository 처리 (동적 쿼리)
    Board boardInfo(Long bno);

    // 메인페이지
    //최근 올라온 장소 게시글 10개

    // 가장 많은 추천을 받은 코스 게시글 - 메인

    //코스에 해당하는 1일차 코스

    //팔로워들의 게시글 - 메인

    //광고 게시글 - 메인

    //

    //   회원별 장소 포스팅 조회


    // 장소별 장소 포스팅 조회

    // 회원별 코스 포스팅 조회

    //코스 검색 조회

    // place ,course 검색 (동적 쿼리)

}
