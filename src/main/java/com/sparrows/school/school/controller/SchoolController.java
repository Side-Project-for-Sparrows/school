package com.sparrows.school.school.controller;

import com.sparrows.school.school.model.dto.internal.SchoolValidateRequest;
import com.sparrows.school.school.model.entity.SchoolEntity;
import com.sparrows.school.school.port.in.SchoolUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolUseCase schoolUseCase;

    @GetMapping("/school/search")
    public List<SchoolEntity> getSchoolByConsonant(@RequestBody String query){
        return schoolUseCase.getSchoolByInitialConsonant(query);
    }

    @PostMapping("/school/validate")
    public boolean validateSchool(@RequestBody SchoolValidateRequest request){
        log.info("validateSchool");
        return schoolUseCase.isSameLocationWithSchoolId(request.getSchoolId(), request.getLatitude(), request.getLongitude());
    }
}
