package com.sparrows.school.school.model.dto.internal;

import com.sparrows.school.school.model.entity.SchoolEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolSaveRequest {
    Long id;
    String name;

    public static SchoolSaveRequest from(SchoolEntity entity){
        return new SchoolSaveRequest(((Integer)(entity.getId())).longValue(), entity.getName());
    }
}
