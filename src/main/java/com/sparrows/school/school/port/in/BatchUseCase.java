package com.sparrows.school.school.port.in;

import com.sparrows.school.school.model.dto.SchoolBatchRequestDto;
import com.sparrows.school.school.model.entity.BatchJobEntity;

import java.util.List;

public interface BatchUseCase {
    void executeManualBatch(SchoolBatchRequestDto requestDto);

    List<BatchJobEntity> getBatchJobList();
}
