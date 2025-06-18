package com.sparrows.school.school.adapter.out;

import com.sparrows.school.school.adapter.repository.SchoolRepository;
import com.sparrows.school.school.model.entity.SchoolEntity;
import com.sparrows.school.school.port.out.SchoolRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SchoolAdapter implements SchoolRepositoryPort {

    private final SchoolRepository schoolRepository;

    public SchoolAdapter(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public Optional<SchoolEntity> findByStdCode(String stdCode) {
        return schoolRepository.findByStdCode(stdCode);
    }

    @Override
    public SchoolEntity save(SchoolEntity school) {
        return schoolRepository.save(school);
    }

    @Override
    public Optional<SchoolEntity> findSchoolById(int schoolId) {
        return schoolRepository.findById(schoolId);
    }
}