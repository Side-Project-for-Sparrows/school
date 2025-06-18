package com.sparrows.school.school.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchoolBatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job schoolJob;

    // 매주 월요일 새벽 3시 실행
//    @Scheduled(cron = "0 0 3 ? * MON")
    @Scheduled(cron = "0 58 11 * * ?")
    public void runSchoolBatchJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addString("runAt", LocalDateTime.now().toString())
                    .toJobParameters();

            jobLauncher.run(schoolJob, params);
            log.info("✅ 학교 정보 배치 실행 완료");
        } catch (Exception e) {
            log.error("❌ 학교 정보 배치 실행 실패", e);
        }
    }
}
