package com.healthtrip.travelcare.repository.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder

public class AddressResponse {

    @Schema(description = "")
    private String address1;
    @Schema(description = "상세주소")
    private String address2;
    @Schema(description = "지역: 서구,강남 등")
    private String district;
    @Schema(description = "시: 인천,서울 등")
    private String cityName;
    private String postalCode;
    private String countryName;
}
