package com.sparrows.school.school.port.in;

import com.sparrows.school.school.model.dto.AdminSchoolRequestDto;
import com.sparrows.school.school.model.entity.UnknownSchoolEntity;

import java.util.List;

public interface AdminUseCase {
    List<UnknownSchoolEntity> getUnknownSchoolList();
    void insertUnknownSchool(AdminSchoolRequestDto dto);
}
