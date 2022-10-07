package com.healthtrip.travelcare.repository.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalCheckupItemReq {
    private String name;
    private String description;
}
