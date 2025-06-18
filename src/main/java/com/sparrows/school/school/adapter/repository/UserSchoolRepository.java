package com.sparrows.school.school.adapter.repository;

import com.sparrows.school.school.model.entity.UserSchoolRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSchoolRepository extends JpaRepository<UserSchoolRelationEntity, Long>  {
}
