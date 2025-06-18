package com.sparrows.school.school.port.in;

public interface UserSchoolUseCase {
    void addInitializeUserSchoolRelation(Long userId, Integer schoolId);

    void addAdditionalSchoolRelation(Long userId, Integer schoolId);
}
