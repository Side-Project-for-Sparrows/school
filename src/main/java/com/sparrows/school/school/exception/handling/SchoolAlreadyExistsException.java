package com.sparrows.school.school.exception.handling;

import com.sparrows.school.school.exception.BusinessException;
import com.sparrows.school.school.exception.SchoolErrorCode;

public class SchoolAlreadyExistsException extends BusinessException {
    public SchoolAlreadyExistsException() {
        super(SchoolErrorCode.SCHOOL_ALREADY_EXISTS);
    }
}
