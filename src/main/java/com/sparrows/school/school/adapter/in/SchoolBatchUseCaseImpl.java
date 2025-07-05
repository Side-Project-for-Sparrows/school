package com.sparrows.school.school.adapter.in;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrows.school.school.exception.handling.FailNeisApiIntegrationException;
import com.sparrows.school.school.model.dto.CreateSchoolRequestDto;
import com.sparrows.school.school.model.dto.SchoolProcessResultDto;
import com.sparrows.school.school.model.entity.SchoolEntity;
import com.sparrows.school.school.model.entity.UnknownSchoolEntity;
import com.sparrows.school.school.model.enums.SchoolType;
import com.sparrows.school.school.port.in.SchoolBatchUseCase;
import com.sparrows.school.school.port.out.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class SchoolBatchUseCaseImpl implements SchoolBatchUseCase {
    @Value("${neis.url}")
    private String baseUrl;

    @Value("${neis.api-key}")
    private String apiKey;

    Map<String, SchoolType> schoolTypeMap = new HashMap<>();

    private final UnknownSchoolRepositoryPort unknownSchoolRepositoryPort;
    private final SchoolTypeRepositoryPort schoolTypeRepositoryPort;
    private final SchoolRepositoryPort schoolRepositoryPort;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final SchoolEventPort schoolEventPort;

    @Transactional(rollbackOn = Exception.class)
    public void executeBatch(String targetDate) {
        CreateSchoolRequestDto requestDto = new CreateSchoolRequestDto(targetDate);
        initSchoolTypeMap();
        List<JsonNode> schoolJsonList = fetchSchoolJsonList(requestDto);
        List<SchoolProcessResultDto> processed = processSchoolJsonList(schoolJsonList);

        saveOrUpdateSchool(processed);

        log.info("✅ 배치 완료 - 기준일자: {}", targetDate);
    }

    private void initSchoolTypeMap() {
        schoolTypeRepositoryPort.findAll().forEach(entity -> {
            schoolTypeMap.put(entity.getTypeName(), entity.getTypeEnum());
        });

        log.info("✅ schoolTypeMap 초기화 완료: {}", schoolTypeMap);
    }

    private List<JsonNode> fetchSchoolJsonList(CreateSchoolRequestDto request) {
        List<JsonNode> schoolJsonList = new ArrayList<>();
        int pageIndex = 1;
        int pageSize = 1000;

        while (true) {
            try {
                String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                        .queryParam("KEY", apiKey)
                        .queryParam("Type", "json")
                        .queryParam("pIndex", pageIndex)
                        .queryParam("pSize", pageSize)
                        .toUriString();

                String jsonResponse = restTemplate.getForObject(url, String.class);
                JsonNode root = objectMapper.readTree(jsonResponse);
                JsonNode rowData = root.path("schoolInfo").path(1).path("row");

                if (rowData.isEmpty() || !rowData.isArray()) break;

                for (JsonNode schoolNode : rowData) {
                    String loadDtm = schoolNode.path("LOAD_DTM").asText();

                    if (loadDtm.compareTo(request.getDate()) <= 0) continue;
                    schoolJsonList.add(schoolNode);
                }
                pageIndex++;

            } catch (Exception e) {
                log.error("Error while calling NEIS API", e);
                throw new FailNeisApiIntegrationException();
            }
        }
        return schoolJsonList;
    }

    private List<SchoolProcessResultDto> processSchoolJsonList(List<JsonNode> schoolJsonList) {
        List<SchoolProcessResultDto> results = new ArrayList<>();

        for (JsonNode schoolNode : schoolJsonList) {
            String stdCode = schoolNode.path("SD_SCHUL_CODE").asText();
            if (stdCode.trim().isEmpty()) continue;

            String schoolName = schoolNode.path("SCHUL_NM").asText();
            String address = schoolNode.path("ORG_RDNMA").asText();
            String loadDtm = schoolNode.path("LOAD_DTM").asText();
            String schoolTypeName = schoolNode.path("SCHUL_KND_SC_NM").asText();
            SchoolType schoolType = schoolTypeMap.get(schoolTypeName);

            results.add(new SchoolProcessResultDto(stdCode, schoolName, schoolTypeName, address, loadDtm, schoolType));
        }

        return results;
    }

    private void saveOrUpdateSchool(List<SchoolProcessResultDto> schoolProcessResultList) {
        for (SchoolProcessResultDto school : schoolProcessResultList) {
            if (school.getSchoolType() == null) {
                saveUnknownSchool(school);
                continue;
            }

            if (school.getSchoolType() == SchoolType.ELEMENTARY) {
                log.info("Skipping elementary school: {}", school.getSchoolName());
                continue;
            }

            saveOrUpdateSchoolEntity(school);
        }
    }

    private void saveUnknownSchool(SchoolProcessResultDto processResult) {
        UnknownSchoolEntity unknownSchool = processResult.createUnknown();
        boolean exists = unknownSchoolRepositoryPort.existsByStdCode(unknownSchool.getStdCode());

        if (!exists) {
            unknownSchoolRepositoryPort.save(unknownSchool);
            log.info("Saved unknown school: {}", processResult.getSchoolName());
        } else {
            log.warn("Unknown school already exists - stdCode: {}, name: {}", unknownSchool.getStdCode(), processResult.getSchoolName());
        }
    }

    private void saveOrUpdateSchoolEntity(SchoolProcessResultDto processResult) {
        Optional<SchoolEntity> school = schoolRepositoryPort.findByStdCode(processResult.getStdCode());
        boolean isNew = school.isEmpty();

        SchoolEntity schoolEntity = null;
        if(isNew){
            log.info("📝 게시판 생성 요청 시작");
            schoolEntity = processResult.create();
            schoolEntity = schoolRepositoryPort.save(schoolEntity);
            log.info("schoolEntity - {}", schoolEntity);
            schoolEventPort.publishSchoolCreatedEvent(schoolEntity.getId(), schoolEntity.getName());
        }

        if(!isNew){
            log.info("🏫 기존 학교 정보 업데이트 - 학교명: {}", processResult.getSchoolName());
            schoolEntity = school.get();
            schoolEntity = processResult.updateSchoolEntity(schoolEntity);
            schoolRepositoryPort.save(schoolEntity);
        }

        log.info("✅ 학교 정보 저장 완료 - 학교 코드: {}", processResult.getStdCode());
    }
}
