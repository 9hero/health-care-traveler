package com.healthtrip.travelcare.repository.dto.request;

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
public class MedicalCheckProgramReq {

    private String programName;

    private MedicalCheckupProgram.ProgramType programType;

    private BigDecimal priceForMan;

    private BigDecimal priceForWoman;

    private String elements;
    private Long checkupProgramRefId;

    private CategorySearchRequest.AddProgramCategory addProgramCategory;
}
