package com.sparrows.school.school.port.out;

import com.sparrows.school.school.model.entity.SchoolEntity;

import java.util.Optional;

public interface SchoolRepositoryPort {

    Optional<SchoolEntity> findByStdCode(String stdCode);

    SchoolEntity save(SchoolEntity school);

    Optional<SchoolEntity> findSchoolById(int schoolId);
}