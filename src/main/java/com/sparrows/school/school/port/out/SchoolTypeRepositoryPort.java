package com.sparrows.school.school.port.out;

import com.sparrows.school.school.model.entity.SchoolTypeEntity;

import java.util.List;

public interface SchoolTypeRepositoryPort {
    long count();
    void saveAll(List<SchoolTypeEntity> schoolTypes);
    List<SchoolTypeEntity> findAll();
}