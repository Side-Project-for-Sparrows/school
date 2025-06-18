package com.sparrows.school.school.port.out;

import com.sparrows.school.school.model.dto.CreateBoardRequestDto;

public interface BoardPort {
    void sendCreateBoardRequest(CreateBoardRequestDto request);
}
