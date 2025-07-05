package com.sparrows.school.school.adapter.in;

import com.sparrows.school.school.exception.handling.SchoolAlreadyExistsException;
import com.sparrows.school.school.exception.handling.UnknownSchoolNotFoundException;
import com.sparrows.school.school.model.dto.AdminSchoolRequestDto;
import com.sparrows.school.school.model.entity.SchoolEntity;
import com.sparrows.school.school.model.entity.SchoolTypeEntity;
import com.sparrows.school.school.model.entity.UnknownSchoolEntity;
import com.sparrows.school.school.model.enums.SchoolType;
import com.sparrows.school.school.port.in.AdminUseCase;
import com.sparrows.school.school.port.out.AdminPort;
import com.sparrows.school.school.port.out.SchoolRepositoryPort;
import com.sparrows.school.school.port.out.SchoolTypeRepositoryPort;
import com.sparrows.school.school.port.out.UnknownSchoolRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AdminUseCaseImpl implements AdminUseCase {
    private final SchoolRepositoryPort schoolRepositoryPort;
    private final AdminPort adminPort;
    private final UnknownSchoolRepositoryPort unknownSchoolRepositoryPort;
    private final SchoolTypeRepositoryPort schoolTypeRepositoryPort;

    public AdminUseCaseImpl(SchoolRepositoryPort schoolRepositoryPort, AdminPort adminPort, UnknownSchoolRepositoryPort unknownSchoolRepositoryPort, SchoolTypeRepositoryPort schoolTypeRepositoryPort) {
        this.schoolRepositoryPort = schoolRepositoryPort;
        this.adminPort = adminPort;
        this.unknownSchoolRepositoryPort = unknownSchoolRepositoryPort;
        this.schoolTypeRepositoryPort = schoolTypeRepositoryPort;
    }

    @Override
    public List<UnknownSchoolEntity> getUnknownSchoolList() {
        return unknownSchoolRepositoryPort.findAll();
    }

    @Override
    @Transactional
    public void insertUnknownSchool(AdminSchoolRequestDto dto) {
        UnknownSchoolEntity unknown = unknownSchoolRepositoryPort.findById(dto.getUnknownSchoolId())
                .orElseThrow(UnknownSchoolNotFoundException::new);

        SchoolTypeEntity type = handleSchoolType(dto.getTypeEnum(), unknown.getType());
        registerSchool(unknown, type);
        markUnknownAsProcessed(unknown);
    }


    private SchoolTypeEntity handleSchoolType(SchoolType typeEnum, String typeName) {
        // 학교 타입 없으면, 저장
        return schoolTypeRepositoryPort.findSchoolTypeByName(typeName)
                .orElseGet(() -> schoolTypeRepositoryPort.save(new SchoolTypeEntity(typeName, typeEnum)));
    }

    private void registerSchool(UnknownSchoolEntity unknown, SchoolTypeEntity type) {
        // 학교 저장
        if (schoolRepositoryPort.findByStdCode(unknown.getStdCode()).isPresent()) {
            throw new SchoolAlreadyExistsException();
        }
        SchoolEntity school = SchoolEntity.builder()
                .stdCode(unknown.getStdCode())
                .name(unknown.getName())
                .type(type.getTypeEnum())
                .address(unknown.getAddress())
                .offlUpdatedAt(unknown.getOfflUpdatedAt())
                .build();
        schoolRepositoryPort.save(school);
    }

    private void markUnknownAsProcessed(UnknownSchoolEntity unknown) {
        // 언노운 학교 삭제 날짜 처리(아에 디비 삭제x)
        unknown.softDelete();
        unknownSchoolRepositoryPort.save(unknown);
    }

}
