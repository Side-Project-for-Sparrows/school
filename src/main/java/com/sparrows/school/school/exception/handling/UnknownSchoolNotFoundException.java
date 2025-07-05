package com.sparrows.school.school.exception.handling;

import com.sparrows.school.school.exception.BusinessException;
import com.sparrows.school.school.exception.SchoolErrorCode;

public class UnknownSchoolNotFoundException extends BusinessException {
    public UnknownSchoolNotFoundException() {
        super(SchoolErrorCode.UNKNOWN_SCHOOL_NOT_FOUND);
    }
}
