package com.sparrows.school.school.adapter.out;

import com.sparrows.school.school.adapter.repository.UnknownSchoolRepository;
import com.sparrows.school.school.model.entity.UnknownSchoolEntity;
import com.sparrows.school.school.port.out.UnknownSchoolRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UnknownSchoolAdapter implements UnknownSchoolRepositoryPort {

    private final UnknownSchoolRepository unknownSchoolRepository;

    public UnknownSchoolAdapter(UnknownSchoolRepository unknownSchoolRepository) {
        this.unknownSchoolRepository = unknownSchoolRepository;
    }

    @Override
    public void save(UnknownSchoolEntity school) {
        unknownSchoolRepository.save(school);
    }

    @Override
    public boolean existsByStdCode(String stdCode) {
        return unknownSchoolRepository.existsByStdCode(stdCode);
    }

    @Override
    public Optional<UnknownSchoolEntity> findById(Integer id) {
        return unknownSchoolRepository.findById(id);
    }

    @Override
    public List<UnknownSchoolEntity> findAll() {
        return unknownSchoolRepository.findAll();
    }
}