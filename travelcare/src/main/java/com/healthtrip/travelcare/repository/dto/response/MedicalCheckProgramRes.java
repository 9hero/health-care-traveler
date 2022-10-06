package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.entity.hospital.MedicalCheckupCategory;
import com.healthtrip.travelcare.entity.hospital.MedicalCheckupProgram;
import com.healthtrip.travelcare.entity.hospital.ProgramCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MCPdetailsAdmin{
        private MedicalCheckProgramRes medicalCheckProgramRes;
        private List<ProgramCategoryRes> programCategoryRes;

        public static MCPdetailsAdmin toResponse(MedicalCheckupProgram programDetailsByIdForAdmin) {
            // 프로그램 정보 담기
            var mcPdetailsAdmin = MCPdetailsAdmin.builder()
                    .medicalCheckProgramRes(MedicalCheckProgramRes.toResponse(programDetailsByIdForAdmin))
                    .build();
            // 프로그램 범주 추가
            var checkupCategoryRes = new ArrayList<ProgramCategoryRes>();
            for (ProgramCategory category : programDetailsByIdForAdmin.getProgramCategories()){
                // 범주 응답객체 추가
                checkupCategoryRes.add(
                        ProgramCategoryRes.builder()
                                .programCategoryId(category.getId())
                                // 의료범주 응답객체 생성
                                .medicalCheckupCategoryRes(MedicalCheckupCategoryRes.toResponse(category.getMedicalCheckupCategory()))
                                .build()
                );
            }
            mcPdetailsAdmin.setProgramCategoryRes(checkupCategoryRes);
            return mcPdetailsAdmin;
        }

    }
}
