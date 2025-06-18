package com.sparrows.school.school.model.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolSearchRequest {
    private String domain;
    private String query;

    public static SchoolSearchRequest from(String query){
        return new SchoolSearchRequest("school",query);
    }
}
