package com.sparrows.school.school.port.out;

import com.sparrows.school.school.model.entity.UnknownSchoolEntity;

import java.util.List;
import java.util.Optional;

public interface UnknownSchoolRepositoryPort {

    void save(UnknownSchoolEntity unknownSchoolEntity);
    boolean existsByStdCode(String stdCode);
    Optional<UnknownSchoolEntity> findById(Integer id);
    List<UnknownSchoolEntity> findAll();
}
