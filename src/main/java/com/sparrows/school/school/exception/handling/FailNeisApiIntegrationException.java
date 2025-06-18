package com.sparrows.school.school.exception.handling;

import com.sparrows.school.school.exception.BusinessException;
import com.sparrows.school.school.exception.SchoolErrorCode;

public class FailNeisApiIntegrationException extends BusinessException {

    public FailNeisApiIntegrationException() {
        super(SchoolErrorCode.FAIL_NEIS_API_INTEGRATION);
    }
}
