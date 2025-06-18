package com.sparrows.school.school.port.out;

import com.sparrows.school.school.model.entity.UserSchoolRelationEntity;

public interface UserSchoolRepositoryPort {
    void save(UserSchoolRelationEntity userSchoolRelationEntity);
}
