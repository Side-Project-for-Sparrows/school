package com.sparrows.school.school.model.dto;

import com.sparrows.school.school.model.entity.SchoolEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateBoardRequestDto {
    Long userId;
    String name;
    Integer schoolId;
    Boolean isPublic;
    String description;

    public CreateBoardRequestDto(Long userId, String name, Integer schoolId, Boolean isPublic, String description){
        this.userId = userId;
        this.name = name;
        this.schoolId = schoolId;
        this.isPublic = isPublic;
        this.description = description;
    }

    public static CreateBoardRequestDto of(SchoolEntity school) {
        return new CreateBoardRequestDto(null, school.getName(), school.getId(), false, school.getName() + "게시판");
    }
}
