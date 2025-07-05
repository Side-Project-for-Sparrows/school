package com.sparrows.school.school.config;

import com.sparrows.school.school.model.entity.SchoolEntity;
import com.sparrows.school.school.model.enums.SchoolType;
import com.sparrows.school.school.port.out.SchoolRepositoryPort;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile({"local", "dev"}) // prod에서는 실행 안 됨
public class SchoolSeedInitializer {

    private final SchoolRepositoryPort schoolRepositoryPort;

    @PostConstruct
    public void init() {
        if (schoolRepositoryPort.count() > 0) return;

        SchoolEntity school = SchoolEntity.builder()
                .stdCode("000000")
                .name("테스트고등학교")
                .type(SchoolType.HIGH)
                .address("테스트주소")
                .offlUpdatedAt("20250615")
                .typeException(false)
                .build();

        schoolRepositoryPort.save(school);
        log.info("[시드 데이터] School 데이터 초기화 완료: {}", school.getName());
    }
}
