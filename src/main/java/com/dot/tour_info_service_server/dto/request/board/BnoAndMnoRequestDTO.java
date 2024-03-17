package com.dot.tour_info_service_server.dto.request.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BnoAndMnoRequestDTO {
    private Long bno;
    private Long mno;
}
