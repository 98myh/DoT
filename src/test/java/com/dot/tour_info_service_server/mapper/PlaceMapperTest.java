package com.dot.tour_info_service_server.mapper;

import com.dot.tour_info_service_server.dto.request.place.RegistPlaceRequestDTO;
import com.dot.tour_info_service_server.entity.Category;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Log4j2
class PlaceMapperTest {

    @Autowired
    private PlaceMapper placeMapper;

    @Test
    void placeRegistTest(){
       log.info("장소 등록 테스트 : "+placeMapper.placeRegist(RegistPlaceRequestDTO.builder()
                       .name("test")
                       .lng(123.1)
                       .lat(123.1)
                       .roadAddress("test~")
                       .localAddress("test~~")
                       .engAddress("test~~~")
                       .category(Category.ETC)
               .build()));
    }
}