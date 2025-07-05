package com.sparrows.school.school.config;

import com.sparrows.school.school.adapter.repository.BatchJobRepository;
import com.sparrows.school.school.model.entity.BatchJobEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchJobInitializer implements CommandLineRunner {
    private final BatchJobRepository batchJobRepository;

    @Override
    public void run(String... args) {
        if (batchJobRepository.count() == 0) {
            batchJobRepository.save(BatchJobEntity.builder().jobName("schoolJob").build());

            log.info("BatchJobInitializer 데이터 초기화 완료");
        }
    }
}