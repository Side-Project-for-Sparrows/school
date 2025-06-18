package com.sparrows.school.school.port.out;

import com.sparrows.school.school.model.dto.internal.SchoolSaveRequest;
import com.sparrows.school.school.model.dto.internal.SchoolSaveResponse;
import com.sparrows.school.school.model.dto.internal.SchoolSearchRequest;
import com.sparrows.school.school.model.dto.internal.SchoolSearchResponse;

public interface SchoolSearchPort {
    SchoolSearchResponse search(SchoolSearchRequest request);
    SchoolSaveResponse save(SchoolSaveRequest request);
}
