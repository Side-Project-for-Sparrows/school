package com.sparrows.school.school.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_school_relation")
public class UserSchoolRelationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "school_id", nullable = false)
    private int schoolId;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary;

    @Column(name = "is_graduated", nullable = false)
    private boolean isGraduated;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;

    public UserSchoolRelationEntity(Long userId, int schoolId, boolean isPrimary, boolean isGraduated) {
        this.userId = userId;
        this.schoolId = schoolId;
        this.isPrimary = isPrimary;
        this.isGraduated = isGraduated;
    }
}
