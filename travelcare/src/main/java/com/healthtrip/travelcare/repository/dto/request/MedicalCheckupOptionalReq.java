package com.healthtrip.travelcare.repository.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalCheckupOptionalReq {
    private Long medicalCheckUpOptionalID;
    private String requesterName;
    private Long personCount;
    private BigDecimal amount;
}
