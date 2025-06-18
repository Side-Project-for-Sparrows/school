package com.sparrows.school.school.model.dto;

import com.sparrows.school.school.model.entity.SchoolEntity;
import com.sparrows.school.school.model.entity.UnknownSchoolEntity;
import com.sparrows.school.school.model.enums.SchoolType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SchoolProcessResultDto {
    String stdCode;
    String schoolName;
    String schoolTypeName;
    String address;
    String loadDtm;
    SchoolType schoolType;

    public SchoolEntity updateSchoolEntity(SchoolEntity original){
        original.update(schoolName, schoolType, address, loadDtm);
        return original;
    }

    public SchoolEntity create(){
        return new SchoolEntity(
                stdCode, schoolName, schoolType, address, loadDtm
        );
    }

    public UnknownSchoolEntity createUnknown(){
        return new UnknownSchoolEntity(
                stdCode, schoolName, schoolTypeName, address, loadDtm);
    }
}