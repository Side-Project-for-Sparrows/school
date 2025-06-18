package com.sparrows.school.school.model.dto;

import com.sparrows.school.school.exception.SchoolErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
public class CreateFailureRequestDto {
    private String reason;
    private String domain;
    private OffsetDateTime reportTime;

    public CreateFailureRequestDto(String reason, String domain, OffsetDateTime reportTime) {
        this.reason = reason;
        this.domain = domain;
        this.reportTime = reportTime;
    }

    public static CreateFailureRequestDto from(SchoolErrorCode exceptionType) {
        return new CreateFailureRequestDto(
                exceptionType.getMessage(),
                "school",
                OffsetDateTime.now()
        );
    }
}

