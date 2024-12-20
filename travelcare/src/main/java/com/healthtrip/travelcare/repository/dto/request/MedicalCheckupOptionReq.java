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
public class MedicalCheckupOptionReq {
    private Long checkupItemId;
    private BigDecimal price;

}
