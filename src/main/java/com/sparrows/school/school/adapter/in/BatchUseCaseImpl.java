package com.sparrows.school.school.adapter.in;

import com.sparrows.school.school.exception.handling.BatchExecutionException;
import com.sparrows.school.school.exception.handling.BatchNotFoundException;
import com.sparrows.school.school.model.dto.SchoolBatchRequestDto;
import com.sparrows.school.school.model.entity.BatchJobEntity;
import com.sparrows.school.school.port.in.BatchUseCase;
import com.sparrows.school.school.port.out.BatchJobRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchUseCaseImpl implements BatchUseCase {

    private final BatchJobRepositoryPort batchJobRepositoryPort;
    private final JobLauncher jobLauncher;
    private final ApplicationContext applicationContext;

    @Override
    public void executeManualBatch(SchoolBatchRequestDto requestDto) {
        BatchJobEntity batchJob = batchJobRepositoryPort.findById(requestDto.getBatchId())
                .orElseThrow(BatchNotFoundException::new);

        String jobName = batchJob.getJobName();
        log.info("📌 [Batch] Manual execution request - jobName: {}, targetDate: {}", jobName, requestDto.getTargetDate());

        try {
            Job job = applicationContext.getBean(jobName, Job.class);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("targetDate", requestDto.getTargetDate())
                    .addString("runAt", LocalDateTime.now().toString())
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(job, jobParameters);

            if (execution.getStatus().isUnsuccessful()) {
                String failMessage = execution.getAllFailureExceptions().stream()
                        .map(Throwable::getMessage)
                        .findFirst()
                        .orElse("Unknown error");

                log.error("🛑 [Batch] Execution failed - jobName: {}, status: {}, message: {}",
                        jobName, execution.getStatus(), failMessage);
                throw new BatchExecutionException();
            }

            log.info("✅ [Batch] Manual execution succeeded - jobName: {}", jobName);

        } catch (BatchExecutionException e) {
            // 배치돌리다 에러났으면 그냥 처리하고, 나머지는 아래 로그 찍히도록 처리
            throw e;
        } catch (Exception e) {
            log.error("🔥 [Batch] Unexpected error - jobName: {}, errorType: {}, message: {}",
                    jobName, e.getClass().getSimpleName(), e.getMessage());
            throw new BatchExecutionException();
        }
    }

    @Override
    public List<BatchJobEntity> getBatchJobList() {
        return batchJobRepositoryPort.findAll();
    }
}
