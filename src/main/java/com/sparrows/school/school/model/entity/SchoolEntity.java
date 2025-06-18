package com.sparrows.school.school.model.entity;

import com.sparrows.school.common.model.BaseEntity;
import com.sparrows.school.school.model.enums.SchoolType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "school")
public class SchoolEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    String stdCode;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SchoolType type;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String offlUpdatedAt;

    @ColumnDefault("false")
    boolean typeException;

    public SchoolEntity(String stdCode, String name, SchoolType type, String address, String offlUpdatedAt) {
        this.stdCode = stdCode;
        this.name = name;
        this.type = type;
        this.address = address;
        this.offlUpdatedAt = offlUpdatedAt;
    }

    public void update(String name, SchoolType type, String address, String loadDtm) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.offlUpdatedAt = loadDtm;
    }
}
