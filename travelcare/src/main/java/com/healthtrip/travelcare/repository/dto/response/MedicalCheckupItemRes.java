package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.hospital.MedicalCheckupItem;
import com.healthtrip.travelcare.entity.hospital.MedicalCheckupItemCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalCheckupItemRes {
    private Long itemId;
    private String name;
    private List<MedicalCheckupCategoryRes> categoryResList;
}
