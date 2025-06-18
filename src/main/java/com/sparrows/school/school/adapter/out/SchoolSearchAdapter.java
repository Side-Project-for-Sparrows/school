package com.sparrows.school.school.adapter.out;

import com.sparrows.school.school.feignclient.SchoolIndexClient;
import com.sparrows.school.school.model.dto.internal.SchoolSaveRequest;
import com.sparrows.school.school.model.dto.internal.SchoolSaveResponse;
import com.sparrows.school.school.model.dto.internal.SchoolSearchRequest;
import com.sparrows.school.school.model.dto.internal.SchoolSearchResponse;
import com.sparrows.school.school.port.out.SchoolSearchPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchoolSearchAdapter implements SchoolSearchPort {
    @Autowired
    SchoolIndexClient schoolIndexClient;

    @Override
    public SchoolSearchResponse search(SchoolSearchRequest request) {
        SchoolSearchResponse response = schoolIndexClient.searchSchool(request);
        return response;
    }

    @Override
    public SchoolSaveResponse save(SchoolSaveRequest request) {
        return schoolIndexClient.saveSchool(request);
    }
}
