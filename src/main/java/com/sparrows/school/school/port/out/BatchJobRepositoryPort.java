package com.sparrows.school.school.port.out;

import com.sparrows.school.school.model.entity.BatchJobEntity;

import java.util.List;
import java.util.Optional;

public interface BatchJobRepositoryPort {
    Optional<BatchJobEntity> findById(Integer id);
    List<BatchJobEntity> findAll();
    BatchJobEntity save(BatchJobEntity entity);
}
