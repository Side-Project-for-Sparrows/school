package com.sparrows.school.school.port.out;

import com.sparrows.school.school.model.entity.SchoolTypeEntity;

import java.util.List;
import java.util.Optional;

public interface SchoolTypeRepositoryPort {
    long count();
    void saveAll(List<SchoolTypeEntity> schoolTypes);
    SchoolTypeEntity save(SchoolTypeEntity schoolTypeEntity);
    List<SchoolTypeEntity> findAll();
    Optional<SchoolTypeEntity> findSchoolTypeByName(String typeName);
}