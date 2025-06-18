package com.sparrows.school.school.exception.handling;

import com.sparrows.school.school.exception.BusinessException;
import com.sparrows.school.school.exception.SchoolErrorCode;

public class FailAdminApiIntegrationException extends BusinessException {

    public FailAdminApiIntegrationException() {
        super(SchoolErrorCode.FAIL_ADMIN_API_INTEGRATION);
    }
}
