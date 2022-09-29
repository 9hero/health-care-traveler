package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.repository.dto.request.CategorySearchRequest;
import com.healthtrip.travelcare.repository.dto.response.HospitalResponse;
import com.healthtrip.travelcare.repository.dto.response.MedicalCheckProgramRes;
import com.healthtrip.travelcare.repository.dto.response.MedicalCheckupCategoryRes;
import com.healthtrip.travelcare.repository.dto.response.MedicalCheckupOptionalRes;
import com.healthtrip.travelcare.repository.hospital.HospitalRepository;
import com.healthtrip.travelcare.repository.hospital.MedicalCheckupCategoryRepo;
import com.healthtrip.travelcare.repository.hospital.MedicalCheckupOptionalRepo;
import com.healthtrip.travelcare.repository.hospital.MedicalCheckupProgramRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final MedicalCheckupProgramRepo programRepo;
    private final MedicalCheckupCategoryRepo checkupCategoryRepo;
    private final MedicalCheckupOptionalRepo medicalCheckupOptionalRepo;
    @Transactional(readOnly = true)
    public List<HospitalResponse> getHospitals() {
        var hospitals = hospitalRepository.findAll();
        return hospitals.stream().map(HospitalResponse::toResponse).collect(Collectors.toList());

    }
    @Transactional(readOnly = true)
    public List<MedicalCheckProgramRes> getProgramsByHospital(Long hospitalId, Pageable pageable) {
        var hospitalPrograms = programRepo.findByHospitalId(hospitalId,pageable);

        return hospitalPrograms.getContent()
                .stream().map(MedicalCheckProgramRes::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MedicalCheckupCategoryRes> getCheckupCategories() {
        return checkupCategoryRepo.findAll().stream().map(MedicalCheckupCategoryRes::toResponse).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<MedicalCheckProgramRes> getProgramsByCategories(CategorySearchRequest request) {
        return programRepo.findByCategoryIds(request.getCategoryId())
                .stream().map(MedicalCheckProgramRes::toResponse).collect(Collectors.toList());

    }
    @Transactional(readOnly = true)
    public MedicalCheckProgramRes.MCPdetails getProgramDetails(Long programId) {
        return MedicalCheckProgramRes.MCPdetails.toResponse(programRepo.programDetailsById(programId));
    }
    @Transactional(readOnly = true)
    public List<MedicalCheckupOptionalRes> getMedicalCheckupOptions(Long hospitalId) {
        var optionalList = medicalCheckupOptionalRepo.findOptions(hospitalId);
        return optionalList.stream().map(medicalCheckupOptional -> {
            return MedicalCheckupOptionalRes.builder()
                    .optionId(medicalCheckupOptional.getId())
                    .manPrice(medicalCheckupOptional.getPriceForMan())
                    .womanPrice(medicalCheckupOptional.getPriceForWoman())
                    .optionName(medicalCheckupOptional.getMedicalCheckupItem().getName())
                    .build();
        }).collect(Collectors.toList());
    }
}
