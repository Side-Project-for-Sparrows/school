package com.sparrows.school.school.config;

import com.sparrows.school.school.port.in.SchoolBatchUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@Slf4j
@Configuration
@EnableScheduling // 스케줄러 활성화
public class SchoolBatchConfig {

    private final SchoolBatchUseCase schoolBatchUseCase;
    private final JobExplorer jobExplorer;

    public SchoolBatchConfig(SchoolBatchUseCase schoolBatchUseCase, JobExplorer jobExplorer) {
        this.schoolBatchUseCase = schoolBatchUseCase;
        this.jobExplorer = jobExplorer;
    }

    @Bean
    public Job schoolJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws DuplicateJobException {
        return new JobBuilder("schoolJob",jobRepository)
                .start(schoolStep(jobRepository,transactionManager))
                .build();
    }

    public Step schoolStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("schoolStep",jobRepository)
                .tasklet(schoolTasklet(),transactionManager)
                .build();
    }

    @Bean
    public Tasklet schoolTasklet() {
        return (contribution, chunkContext) -> {
            // 가장 최근 배치 실행 날짜를 가져옵니다.
            String lastExecutionDate = getLastSuccessfulExecutionDate();
            schoolBatchUseCase.executeBatch(lastExecutionDate); // 배치 실행
            return RepeatStatus.FINISHED;
        };
    }

    private String getLastSuccessfulExecutionDate() {
        return jobExplorer.getJobInstances("schoolJob", 0, 10).stream()
                .flatMap(jobInstance -> jobExplorer.getJobExecutions(jobInstance).stream())
                .filter(execution -> execution.getEndTime() != null)
                .filter(execution -> execution.getStatus() == BatchStatus.COMPLETED)
                .sorted(Comparator.comparing(JobExecution::getEndTime, Comparator.reverseOrder()))
                .map(execution -> execution.getEndTime().toLocalDate().format(DateTimeFormatter.BASIC_ISO_DATE))
                .findFirst()
                .orElse("19000101");
    }
    /*
    최근 실행된 "schoolJob"의 JobInstance 최대 10: 최근 실행된 순서가 아닌 ID 역순
    각 JobInstance에 대해 해당 인스턴스의 모든 실행(JobExecution) 목록
    종료 시간이 없는 실행은 아직 진행 중이거나 중단된 배치 제외
    정상적으로 끝난 배치 실행
    종료 시간을 기준으로 최신 순으로 내림차순 정렬
    정렬된 스트림 중 가장 첫 번째(=가장 최근 실행) 결과
    COMPLETED 상태의 실행이 없다면, 기본값으로 19000101 날짜 반환
    * */
}
