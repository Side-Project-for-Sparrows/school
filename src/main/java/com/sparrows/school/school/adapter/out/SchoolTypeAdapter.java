package com.sparrows.school.school.adapter.out;

import com.sparrows.school.school.adapter.repository.SchoolTypeRepository;
import com.sparrows.school.school.model.entity.SchoolTypeEntity;
import com.sparrows.school.school.port.out.SchoolTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SchoolTypeAdapter implements SchoolTypeRepositoryPort {

    private final SchoolTypeRepository schoolTypeRepository;

    @Override
    public long count() {
        return schoolTypeRepository.count();
    }

    @Override
    public void saveAll(List<SchoolTypeEntity> schoolTypes) {
        schoolTypeRepository.saveAll(schoolTypes);
    }

    @Override
    public SchoolTypeEntity save(SchoolTypeEntity schoolTypeEntity) {
        return schoolTypeRepository.save(schoolTypeEntity);
    }

    @Override
    public List<SchoolTypeEntity> findAll() {
        return schoolTypeRepository.findAll();
    }

    @Override
    public Optional<SchoolTypeEntity> findSchoolTypeByName(String typeName) {
        return schoolTypeRepository.findSchoolTypeByTypeName(typeName);
    }
}
