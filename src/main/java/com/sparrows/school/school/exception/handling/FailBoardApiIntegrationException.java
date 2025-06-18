package com.sparrows.school.school.exception.handling;

import com.sparrows.school.school.exception.BusinessException;
import com.sparrows.school.school.exception.SchoolErrorCode;

public class FailBoardApiIntegrationException extends BusinessException {

    public FailBoardApiIntegrationException() {
        super(SchoolErrorCode.FAIL_BOARD_API_INTEGRATION);
    }
}
