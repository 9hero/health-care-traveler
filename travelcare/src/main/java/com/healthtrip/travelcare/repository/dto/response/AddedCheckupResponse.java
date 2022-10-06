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
public class AddedCheckupResponse {

    private Long id;
    private String optionName;
    private String bookerName;
    private BigDecimal amount;
}
