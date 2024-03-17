package com.dot.tour_info_service_server.mapper;
import com.dot.tour_info_service_server.dto.request.board.BnoAndMnoRequestDTO;
import com.dot.tour_info_service_server.entity.boardLike.BoardLikePK;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardLikeMapper {
    //게시글에 해당하는 좋아요 삭제
    void deleteBoardLikeBoard(int bno);

    //mno를 받을 때 해당하는 좋아요 삭제
    void deleteBoardLikeMno(Long mno);

    // 게시글 좋아요 여부
    boolean existsBoardLike(BnoAndMnoRequestDTO bnoAndMnoRequestDTO);
}
