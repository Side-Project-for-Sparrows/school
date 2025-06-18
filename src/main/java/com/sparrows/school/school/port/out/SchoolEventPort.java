package com.sparrows.school.school.port.out;

public interface SchoolEventPort {
    void publishSchoolCreatedEvent(Integer schoolId, String schoolName);
}
