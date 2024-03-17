package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.mapper.MemberMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// 실 데이터베이스에 연결 시 필요한 어노테이션
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Log4j2
class MemberMapperTest {
    @Autowired
    private MemberMapper memberMapper;

    @Test
    public void findMembertest(){
        log.info("테스트 : "+memberMapper.getUserProfiles(1l));

    }
}