package com.sparrows.school.school.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolBatchRequestDto {
    private Integer batchId;
    private String targetDate; // ex: "20250630"
}