package com.healthtrip.travelcare.repository.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalCheckupOptionalRes {
    private Long optionId;
    private String optionName;
    private BigDecimal manPrice;
    private BigDecimal womanPrice;

}
