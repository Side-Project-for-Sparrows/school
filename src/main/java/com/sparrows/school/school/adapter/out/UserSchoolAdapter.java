package com.sparrows.school.school.adapter.out;

import com.sparrows.school.school.adapter.repository.UserSchoolRepository;
import com.sparrows.school.school.model.entity.UserSchoolRelationEntity;
import com.sparrows.school.school.port.out.UserSchoolRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class UserSchoolAdapter implements UserSchoolRepositoryPort {

    private final UserSchoolRepository userSchoolRepository;

    public UserSchoolAdapter(UserSchoolRepository userSchoolRepository) {
        this.userSchoolRepository = userSchoolRepository;
    }

    @Override
    public void save(UserSchoolRelationEntity userSchoolRelationEntity) {
        userSchoolRepository.save(userSchoolRelationEntity);
    }
}
