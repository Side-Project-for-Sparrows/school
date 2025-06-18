package com.sparrows.school.school.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateSchoolRequestDto {

    @NotBlank
    private String date;
}
