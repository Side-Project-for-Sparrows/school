package com.sparrows.school.school.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SchoolErrorCode {
    // Global
    INVALID_REQUEST(HttpStatus.UNPROCESSABLE_ENTITY, "INVALID REQUEST ERROR"),
    INTERNAL_SERVER(HttpStatus.UNPROCESSABLE_ENTITY, "INTERNAL SERVER ERROR"),

    FAIL_NEIS_API_INTEGRATION(HttpStatus.BAD_GATEWAY, "FAIL TO INTEGRATE WITH NEIS API"),
    FAIL_BOARD_API_INTEGRATION(HttpStatus.BAD_GATEWAY, "FAIL TO INTEGRATE WITH BOARD API"),
    FAIL_ADMIN_API_INTEGRATION(HttpStatus.BAD_GATEWAY, "FAIL TO INTEGRATE WITH ADMIN API"),
    FAIL_LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND, "FAIL TO LOCATE THE ADDRESS"),
    SCHOOL_NOT_FOUND(HttpStatus.NOT_FOUND, "School not found"),
    UNKNOWN_SCHOOL_NOT_FOUND(HttpStatus.NOT_FOUND, "UNKNOWN SCHOOL NOT FOUND"),
    SCHOOL_ALREADY_EXISTS(HttpStatus.CONFLICT, "School already exists"),

    BATCH_NOT_FOUND(HttpStatus.NOT_FOUND, "Batch not found."),
    BATCH_EXECUTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Batch Error During Admin Batch Execution");

    private final HttpStatus status;
    private final String message;
}
