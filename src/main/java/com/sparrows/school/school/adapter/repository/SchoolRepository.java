package com.sparrows.school.school.adapter.repository;

import com.sparrows.school.school.model.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Integer> {

    Optional<SchoolEntity> findByStdCode(String stdCode);
}