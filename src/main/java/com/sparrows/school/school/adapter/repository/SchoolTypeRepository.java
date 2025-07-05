package com.sparrows.school.school.adapter.repository;

import com.sparrows.school.school.model.entity.SchoolTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolTypeRepository extends JpaRepository<SchoolTypeEntity, Integer> {
    Optional<SchoolTypeEntity> findSchoolTypeByTypeName(String typeName);
}
