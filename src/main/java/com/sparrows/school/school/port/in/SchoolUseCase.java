package com.sparrows.school.school.port.in;

import com.sparrows.school.school.model.entity.SchoolEntity;

import java.util.List;

public interface SchoolUseCase {
    boolean isSameLocationWithSchoolId(int schoolId, double latitude, double longitude);

    List<SchoolEntity> getSchoolByInitialConsonant(String query);

    void insertSchool(SchoolEntity school);
}
