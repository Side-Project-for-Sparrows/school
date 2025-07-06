package com.sparrows.school.school.controller;


import com.sparrows.school.school.model.dto.AdminSchoolRequestDto;
import com.sparrows.school.school.model.dto.SchoolBatchRequestDto;
import com.sparrows.school.school.model.entity.BatchJobEntity;
import com.sparrows.school.school.model.entity.UnknownSchoolEntity;
import com.sparrows.school.school.port.in.AdminUseCase;
import com.sparrows.school.school.port.in.BatchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/school/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BatchUseCase batchUseCase;
    private final AdminUseCase adminUseCase;

    @GetMapping("/school/admin/batch/list")
    public ResponseEntity<List<BatchJobEntity>> list() {
        return ResponseEntity.ok(batchUseCase.getBatchJobList());
    }

    @PostMapping("/school/admin/batch/execute")
    public ResponseEntity<String> execute(@RequestBody SchoolBatchRequestDto dto) {
        batchUseCase.executeManualBatch(dto);
        return ResponseEntity.ok("배치 실행 완료");
    }

        @GetMapping("/school/admin/unknown/list")
    public ResponseEntity<List<UnknownSchoolEntity>> listUnknown() {
        return ResponseEntity.ok(adminUseCase.getUnknownSchoolList());
    }

    @PostMapping("/school/admin/unknown/add")
    public ResponseEntity<String> addUnknown(@RequestBody AdminSchoolRequestDto dto) {
        adminUseCase.insertUnknownSchool(dto);
        return ResponseEntity.ok("unknown 학교 삽입 완료");
    }
}
