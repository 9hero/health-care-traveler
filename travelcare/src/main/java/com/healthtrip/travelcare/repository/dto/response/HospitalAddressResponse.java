package com.healthtrip.travelcare.repository.dto.response;
import com.healthtrip.travelcare.entity.hospital.HospitalAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalAddressResponse {
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
    private String simpleAddress;

    public static HospitalAddressResponse toResponse(HospitalAddress hospitalAddress) {
        var response = HospitalAddressResponse.builder()
                .address1(hospitalAddress.getAddress())
                .address2(hospitalAddress.getAddressDetail())
                .cityName(hospitalAddress.getCity())
                .district(hospitalAddress.getDistrict())
                .postalCode(hospitalAddress.getPostalCode())
                .countryName(hospitalAddress.getCountry().getName())
                .build();
        response.setSimpleAddress();
        return response;
    }

    public void setSimpleAddress() {
        simpleAddress = cityName + " "+district + " "+address1 + " "+address2 + " "+postalCode;
    }
}
