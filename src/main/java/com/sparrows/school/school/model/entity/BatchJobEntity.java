package com.sparrows.school.school.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "batch_job")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchJobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "job_name", nullable = false, unique = true)
    private String jobName;
}
