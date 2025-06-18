package com.sparrows.school.school.adapter.repository;

import com.sparrows.school.school.model.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Integer> {

    Optional<SchoolEntity> findByStdCode(String stdCode);

//    @Modifying
//    @Query(value = """
//            MERGE INTO school AS target
//            USING (VALUES (:address, :name, :stdCode, :type, :offlUpdatedAt))
//                AS source (address, name, std_code, type, offl_updated_at)
//            ON target.std_code = source.std_code
//            WHEN MATCHED THEN
//                UPDATE SET
//                    address = source.address,
//                    name = source.name,
//                    type = source.type,
//                    offl_updated_at = source.offl_updated_at
//            WHEN NOT MATCHED THEN
//                INSERT (address, name, std_code, type, offl_updated_at)
//                VALUES (source.address, source.name, source.std_code, source.type, source.offl_updated_at);
//            """, nativeQuery = true)
//    void upsertSchool(
//            @Param("address") String address,
//            @Param("name") String name,
//            @Param("stdCode") String stdCode,
//            @Param("type") String type,
//            @Param("offlUpdatedAt") String offlUpdatedAt
//    );
}