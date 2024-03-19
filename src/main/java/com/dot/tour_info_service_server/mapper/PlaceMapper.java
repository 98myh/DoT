package com.dot.tour_info_service_server.mapper;

import com.dot.tour_info_service_server.dto.request.place.RegistPlaceRequestDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlaceMapper {
    //장소 등록
    int placeRegist(RegistPlaceRequestDTO registPlaceRequestDTO);


}
