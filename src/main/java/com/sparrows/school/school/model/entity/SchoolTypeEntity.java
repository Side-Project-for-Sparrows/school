package com.sparrows.school.school.model.entity;

import com.sparrows.school.school.model.enums.SchoolType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "school_type")
public class SchoolTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "type_name")
    private String typeName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type_enum")
    private SchoolType typeEnum;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
