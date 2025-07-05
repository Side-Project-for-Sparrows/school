package com.sparrows.school.school.model.dto;

import com.sparrows.school.school.model.enums.SchoolType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminSchoolRequestDto {
    private Integer unknownSchoolId;
        private SchoolType typeEnum;
}