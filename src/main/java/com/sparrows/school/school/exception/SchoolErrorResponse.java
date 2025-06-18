package com.sparrows.school.school.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SchoolErrorResponse {

    private final int status;
    private final String message;

    private SchoolErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static SchoolErrorResponse of(BusinessException e) {
        return SchoolErrorResponse.builder()
                .status(e.getStatus().value())
                .message(e.getMessage())
                .build();
    }

    public static SchoolErrorResponse of(SchoolErrorCode e) {
        return SchoolErrorResponse.builder()
                .status(e.getStatus().value())
                .message(e.getMessage())
                .build();
    }
}

