package com.sparrows.school.school.adapter.repository;

import com.sparrows.school.school.model.entity.BatchJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BatchJobRepository extends JpaRepository<BatchJobEntity, Integer> {
    Optional<BatchJobEntity> findById(Integer id);
}
