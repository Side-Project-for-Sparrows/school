package com.sparrows.school.school.adapter.in;

import com.sparrows.school.school.model.entity.UserSchoolRelationEntity;
import com.sparrows.school.school.port.in.UserSchoolUseCase;
import com.sparrows.school.school.port.out.UserSchoolRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserSchoolUseCaseImpl implements UserSchoolUseCase {

    @Autowired
    private UserSchoolRepositoryPort userSchoolRepositoryPort;

    @Override
    @Transactional
    public void addInitializeUserSchoolRelation(Long userId, Integer schoolId) {
        UserSchoolRelationEntity relation = UserSchoolRelationEntity.builder()
                .userId(userId)
                .schoolId(schoolId)
                .isPrimary(true)
                .isGraduated(false)
                .build();

        userSchoolRepositoryPort.save(relation);
    }

    @Override
    @Transactional
    public void addAdditionalSchoolRelation(Long userId, Integer schoolId) {
        UserSchoolRelationEntity relation = UserSchoolRelationEntity.builder()
                .userId(userId)
                .schoolId(schoolId)
                .isPrimary(false)
                .isGraduated(false)
                .build();

        userSchoolRepositoryPort.save(relation);
    }

}
