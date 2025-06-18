package com.sparrows.school.school.model.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolValidateRequest {
    private int schoolId;
    double latitude;
    double longitude;
}
