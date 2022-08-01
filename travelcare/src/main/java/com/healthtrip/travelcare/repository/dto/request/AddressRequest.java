package com.healthtrip.travelcare.repository.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Schema(name = "주소 등록 Request")
public class AddressRequest {
    private String address1;
    private String address2;
    private String district;
    private String cityName;
    private String postalCode;
    private Long countryId;
}
