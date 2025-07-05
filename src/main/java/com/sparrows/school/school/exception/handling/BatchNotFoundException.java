package com.sparrows.school.school.exception.handling;

import com.sparrows.school.school.exception.BusinessException;
import com.sparrows.school.school.exception.SchoolErrorCode;

public class BatchNotFoundException extends BusinessException {
    public BatchNotFoundException() {
        super(SchoolErrorCode.BATCH_NOT_FOUND);
    }
}
