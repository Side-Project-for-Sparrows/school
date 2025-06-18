package com.sparrows.school.school.port.out;

import com.sparrows.school.school.model.dto.CreateFailureRequestDto;

public interface AdminPort {
    void sendCreateFailureRequest(CreateFailureRequestDto request);
}
