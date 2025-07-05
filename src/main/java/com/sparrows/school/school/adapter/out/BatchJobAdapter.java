package com.sparrows.school.school.adapter.out;

import com.sparrows.school.school.adapter.repository.BatchJobRepository;
import com.sparrows.school.school.model.entity.BatchJobEntity;
import com.sparrows.school.school.port.out.BatchJobRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BatchJobAdapter implements BatchJobRepositoryPort {

    private final BatchJobRepository batchJobRepository;

    public BatchJobAdapter(BatchJobRepository batchJobRepository) {
        this.batchJobRepository = batchJobRepository;
    }

    @Override
    public Optional<BatchJobEntity> findById(Integer id) {
        return batchJobRepository.findById(id);
    }

    @Override
    public List<BatchJobEntity> findAll() {
        return batchJobRepository.findAll();
    }

    @Override
    public BatchJobEntity save(BatchJobEntity entity) {
        return batchJobRepository.save(entity);
    }
}
