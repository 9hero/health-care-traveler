package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.repository.dto.response.HospitalAddressResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalAddressRequest {
    @Schema(description = "일반주소")
    private String address1;
    @Schema(description = "상세주소")
    private String address2;
    @Schema(description = "지역: 서구,강남 등")
    private String district;
    @Schema(description = "시: 인천,서울 등")
    private String cityName;
    @Schema(description ="우편번호")
    private String postalCode;

    private Long countryId;
}
