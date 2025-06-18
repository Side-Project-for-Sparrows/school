package com.sparrows.school.school.config;

import com.sparrows.school.school.model.entity.SchoolTypeEntity;
import com.sparrows.school.school.model.enums.SchoolType;
import com.sparrows.school.school.port.out.SchoolTypeRepositoryPort;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile({"local", "dev"}) // prod에서는 실행 안 됨!
public class SchoolTypeSeedInitializer {

    private final SchoolTypeRepositoryPort schoolTypeRepositoryPort;

    @PostConstruct
    public void init() {
        if (schoolTypeRepositoryPort.count() > 0) return;

        List<SchoolTypeEntity> seedData = List.of(
                // 기존 고등학교 등
                create("고등학교", SchoolType.HIGH),
                create("각종학교(고)", SchoolType.HIGH),
                create("재외한국학교(고)", SchoolType.HIGH),
                create("평생학교(고)-2년6학기", SchoolType.HIGH),
                create("평생학교(고)-3년6학기", SchoolType.HIGH),
                create("방송통신고등학교", SchoolType.HIGH),
                create("고등공민학교", SchoolType.HIGH),
                create("고등기술학교", SchoolType.HIGH),
                create("공동실습소", SchoolType.HIGH),

                // 중학교
                create("중학교", SchoolType.MIDDLE),
                create("각종학교(중)", SchoolType.MIDDLE),
                create("재외한국학교(중)", SchoolType.MIDDLE),
                create("평생학교(중)-2년6학기", SchoolType.MIDDLE),
                create("평생학교(중)-3년6학기", SchoolType.MIDDLE),
                create("방송통신중학교", SchoolType.MIDDLE),

                // 초등학교
                create("초등학교", SchoolType.ELEMENTARY),
                create("재외한국학교(초)", SchoolType.ELEMENTARY),
                create("평생학교(초)-3년6학기", SchoolType.ELEMENTARY),

                // 통합학교
                create("각종학교(대안학교)", SchoolType.INTEGRATED),
                create("각종학교(외국인학교)", SchoolType.INTEGRATED),
                create("국제학교", SchoolType.INTEGRATED),
                create("외국인학교", SchoolType.INTEGRATED),
                create("특수학교", SchoolType.INTEGRATED)
        );

        schoolTypeRepositoryPort.saveAll(seedData);
        log.info("[시드 데이터] SchoolType 데이터 초기화 완료");
    }

    private SchoolTypeEntity create(String typeName, SchoolType typeEnum) {
        return SchoolTypeEntity.builder()
                .typeName(typeName)
                .typeEnum(typeEnum)
                .build();
    }
}
