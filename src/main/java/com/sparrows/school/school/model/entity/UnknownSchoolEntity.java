package com.sparrows.school.school.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "unknown_school")
public class UnknownSchoolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String stdCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String offlUpdatedAt;

    @ColumnDefault("false")
    private boolean typeException;

    public UnknownSchoolEntity(String stdCode, String name, String type, String address, String offlUpdatedAt) {
        this.stdCode = stdCode;
        this.name = name;
        this.type = type;
        this.address = address;
        this.offlUpdatedAt = offlUpdatedAt;
    }
}
