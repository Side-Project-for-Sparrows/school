package com.sparrows.school.school.feignclient;

import com.sparrows.school.school.model.dto.internal.SchoolSaveRequest;
import com.sparrows.school.school.model.dto.internal.SchoolSaveResponse;
import com.sparrows.school.school.model.dto.internal.SchoolSearchRequest;
import com.sparrows.school.school.model.dto.internal.SchoolSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "school-index-service", url = "${search.service.url}")
public interface SchoolIndexClient {
    @PostMapping("/index/school")
    SchoolSearchResponse searchSchool(SchoolSearchRequest request);

    @PostMapping("/index/save")
    SchoolSaveResponse saveSchool(SchoolSaveRequest request);
}
