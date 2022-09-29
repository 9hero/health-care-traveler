package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.hospital.MedicalCheckupProgram;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalCheckProgramRes {
    private Long id;

    private String programName;

    private MedicalCheckupProgram.ProgramType programType;

    private BigDecimal priceForMan;

    private BigDecimal priceForWoman;

    private String elements;

    public static MedicalCheckProgramRes toResponse(MedicalCheckupProgram medicalCheckupProgram) {
        return MedicalCheckProgramRes.builder()
                .id(medicalCheckupProgram.getId())
                .programName(medicalCheckupProgram.getProgramName())
                .programType(medicalCheckupProgram.getProgramType())
                .priceForWoman(medicalCheckupProgram.getPriceForWoman())
                .priceForMan(medicalCheckupProgram.getPriceForMan())
                .elements(medicalCheckupProgram.getElements())
                .build();

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MCPdetails{
        private MedicalCheckProgramRes medicalCheckProgramRes;
        private HospitalResponse.WithAddressHR hospitalResponse;

        public static MCPdetails toResponse(MedicalCheckupProgram medicalCheckupProgram) {
            return MCPdetails.builder()
                    .medicalCheckProgramRes(MedicalCheckProgramRes.toResponse(medicalCheckupProgram))
                    .hospitalResponse(HospitalResponse.WithAddressHR.toResponse(medicalCheckupProgram.getHospital()))
                    .build();
        }
    }
}
