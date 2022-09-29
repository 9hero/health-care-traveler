package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.hospital.MedicalCheckupCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalCheckupCategoryRes {
    private Long id;

    private String name;
    public static MedicalCheckupCategoryRes toResponse(MedicalCheckupCategory medicalCheckupCategory) {
        return MedicalCheckupCategoryRes.builder()
                .id(medicalCheckupCategory.getId())
                .name(medicalCheckupCategory.getName())
                .build();

    }
}
