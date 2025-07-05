package com.sparrows.school.school.exception.handling;

import com.sparrows.school.school.exception.BusinessException;
import com.sparrows.school.school.exception.SchoolErrorCode;

public class BatchExecutionException extends BusinessException {
    public BatchExecutionException() {
        super(SchoolErrorCode.BATCH_EXECUTION_FAILED);
    }
}
