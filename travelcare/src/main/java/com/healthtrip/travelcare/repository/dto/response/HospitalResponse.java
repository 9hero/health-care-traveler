package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.hospital.Hospital;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalResponse {
    private Long hospitalId;
    private String name;
    public static HospitalResponse toResponse(Hospital hospital) {
        return HospitalResponse.builder()
                .hospitalId(hospital.getId())
                .name(hospital.getName())
                .build();
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class WithAddressHR {
        private HospitalResponse hospitalResponse;
        private HospitalAddressResponse hospitalAddressRes;
        public static WithAddressHR toResponse(Hospital hospital) {
            return WithAddressHR.builder()
                    .hospitalResponse(HospitalResponse.toResponse(hospital))
                    .hospitalAddressRes(HospitalAddressResponse.toResponse(hospital.getHospitalAddress()))
                    .build();
        }
    }

}
