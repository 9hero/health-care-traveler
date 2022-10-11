package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.entity.hospital.*;
import com.healthtrip.travelcare.repository.dto.request.*;
import com.healthtrip.travelcare.repository.dto.response.*;
import com.healthtrip.travelcare.repository.hospital.*;
import com.healthtrip.travelcare.repository.location.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final CountryRepository countryRepository;
    private final HospitalAddressRepo hospitalAddressRepo;
    private final HospitalRepository hospitalRepository;
    private final MedicalCheckupProgramRepo programRepo;
    private final MedicalCheckupCategoryRepo checkupCategoryRepo;
    private final MedicalCheckupOptionalRepo medicalCheckupOptionalRepo;
    private final ProgramCheckupCategoriesRepo programCategoryRepo;

    private final MedicalCheckupItemRepo medicalCheckupItemRepo;

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
    public List<MedicalCheckProgramRes.MCPdetailsWithHospital> getProgramsByCategories(CategorySearchRequest request) {
        var categoryIds = request.getCategoryId();
        return programRepo.findByCategoryIds(categoryIds, (long) categoryIds.size())
                .stream().map(MedicalCheckProgramRes.MCPdetailsWithHospital::toResponse).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public MedicalCheckProgramRes.MCPdetails getProgramDetails(Long programId) {
        var medicalCheckupProgram = programRepo.programDetailsById(programId);
        if (medicalCheckupProgram != null){
            return MedicalCheckProgramRes.MCPdetails.toResponse(medicalCheckupProgram);
        }else {
            return null;
        }
    }
    @Transactional(readOnly = true)
    public List<MedicalCheckupOptionalRes> getMedicalCheckupOptions(Long hospitalId) {
        var optionalList = medicalCheckupOptionalRepo.findOptions(hospitalId);
        return optionalList.stream().map(medicalCheckupOptional -> {
            return MedicalCheckupOptionalRes.builder()
                    .optionId(medicalCheckupOptional.getId())
                    .price(medicalCheckupOptional.getPrice())
                    .optionName(medicalCheckupOptional.getMedicalCheckupItem().getName())
                    .build();
        }).collect(Collectors.toList());
    }


    /* admin api */
    @Transactional
    public void newHospital(HospitalRequest hospitalRequest) {
        var hospitalAddressRequest = hospitalRequest.getHospitalAddressRequest();
        HospitalAddress hospitalAddress = HospitalAddress.builder()
                .postalCode(hospitalAddressRequest.getPostalCode())
                .district(hospitalAddressRequest.getDistrict())
                .city(hospitalAddressRequest.getCityName())
                .address(hospitalAddressRequest.getAddress1())
                .addressDetail(hospitalAddressRequest.getAddress2())
                .country(countryRepository.getById(hospitalAddressRequest.getCountryId()))
                .build();
        var savedAddress = hospitalAddressRepo.save(hospitalAddress);
        hospitalRepository.save(Hospital.builder()
                .name(hospitalRequest.getName())
                .description(hospitalRequest.getDescription())
                .hospitalAddress(savedAddress)
                .simpleAddress(hospitalRequest.getSimpleAddress())
                .build());
    }
    @Transactional(readOnly = true)
    public MedicalCheckProgramRes.MCPdetailsAdmin getProgramDetailsForAdmin(Long programId) {
        return MedicalCheckProgramRes.MCPdetailsAdmin.toResponse(programRepo.programDetailsByIdForAdmin(programId));
    }
    @Transactional
    public void addProgram(Long hospitalId, MedicalCheckProgramReq medicalCheckProgramReq) {
        var hospital = hospitalRepository.getById(hospitalId);
        var medicalCheckupProgram = MedicalCheckupProgram.builder()
                .programName(medicalCheckProgramReq.getProgramName())
                .programType(medicalCheckProgramReq.getProgramType())
                .priceForWoman(medicalCheckProgramReq.getPriceForWoman())
                .priceForMan(medicalCheckProgramReq.getPriceForMan())
                .hospital(hospital)
                .elements(medicalCheckProgramReq.getElements())
                .build();
        Long refId = medicalCheckProgramReq.getCheckupProgramRefId();
        boolean hasCheckupProgramRef = refId != null;
        if (hasCheckupProgramRef) {
            var referenceProgram = programRepo.getById(refId);
            medicalCheckupProgram.setReferenceCheckupProgram(referenceProgram);
        }
        programRepo.save(medicalCheckupProgram);
    }
    @Transactional
    public void modifyProgramCategory(Long programId, CategorySearchRequest.ModifyProgramCategory categorySearchRequest) {
        var categoryIds = categorySearchRequest.getCategoryId();

        // 삭제할 범주 ids
        var programCategoryIds = categorySearchRequest.getProgramCategoryIds();
        if (programCategoryIds != null){
            programCategoryRepo.deleteAllByIds(programCategoryIds);
        }
        var program = programRepo.findByIdAndCategoryIdsFetch(programId);
        var programCategories = checkupCategoryRepo.findAllById(categoryIds).stream().map(medicalCheckupCategory -> {
            return ProgramCategory.builder()
                    .medicalCheckupCategory(medicalCheckupCategory)
                    .medicalCheckupProgram(program)
                    .build();
        }).collect(Collectors.toList());
        program.setCategories(programCategories);
    }
    @Transactional
    public void addProgramsToCategories(List<CategorySearchRequest.AddMultipleProgramCategory> categorySearchRequest) {
        // 영속성 캐시 저장
        checkupCategoryRepo.findAll();
        var programIds = categorySearchRequest.stream().map(CategorySearchRequest.AddMultipleProgramCategory::getProgramId).collect(Collectors.toList());
        programRepo.findAllById(programIds);

        for(var programCategoryRequest : categorySearchRequest){
            var medicalCheckupProgram = programRepo.getById(programCategoryRequest.getProgramId());
            addProgramCategory(medicalCheckupProgram,programCategoryRequest.getCategoryId());
        }

    }
    @Transactional
    public void addProgramCategory(MedicalCheckupProgram program, List<Long> categoryIds) {
        var programCategories = checkupCategoryRepo.findAllById(categoryIds).stream().map(medicalCheckupCategory -> {
            return ProgramCategory.builder()
                    .medicalCheckupCategory(medicalCheckupCategory)
                    .medicalCheckupProgram(program)
                    .build();
        }).collect(Collectors.toList());
        program.setCategories(programCategories);
    }




    @Transactional
    public void addProgramAll(Long hospitalId, List<MedicalCheckProgramReq> medicalCheckProgramReqList) {
        var hospital = hospitalRepository.getById(hospitalId);
        List<MedicalCheckupProgram> medicalCheckupPrograms = new ArrayList<>();
        for (MedicalCheckProgramReq medicalCheckProgramReq:medicalCheckProgramReqList){
            var medicalCheckupProgram = MedicalCheckupProgram.builder()
                    .programName(medicalCheckProgramReq.getProgramName())
                    .programType(medicalCheckProgramReq.getProgramType())
                    .priceForWoman(medicalCheckProgramReq.getPriceForWoman())
                    .priceForMan(medicalCheckProgramReq.getPriceForMan())
                    .hospital(hospital)
                    .elements(medicalCheckProgramReq.getElements())
                    .build();

            // 기본검진 참조
            Long refId = medicalCheckProgramReq.getCheckupProgramRefId();
            if (refId != null) {
                var referenceProgram = programRepo.getById(refId);
                medicalCheckupProgram.setReferenceCheckupProgram(referenceProgram);
            }

            // 태그추가
            var categoryIds = medicalCheckProgramReq.getAddProgramCategory().getCategoryId();
            if (categoryIds != null){
                addProgramCategory(medicalCheckupProgram,categoryIds);
            }
            medicalCheckupPrograms.add(medicalCheckupProgram);
        }

        programRepo.saveAll(medicalCheckupPrograms);
    }

    @Transactional
    public void addMedicalCheckupItems(List<MedicalCheckupItemReq> reqList) {
        var medicalCheckupItemList =
                reqList.stream().map(medicalCheckupItemReq ->
                                MedicalCheckupItem.builder()
                                        .name(medicalCheckupItemReq.getName())
                                        .description(medicalCheckupItemReq.getDescription())
                                        .build())
                        .collect(Collectors.toList());
        medicalCheckupItemRepo.saveAll(medicalCheckupItemList);
    }

    @Transactional(readOnly = true)
    public List<MedicalCheckupItemRes> getMedicalCheckupItems() {
        return medicalCheckupItemRepo.findAll().stream().map(medicalCheckupItem -> {
            return MedicalCheckupItemRes.builder()
                    .itemId(medicalCheckupItem.getId())
                    .name(medicalCheckupItem.getName())
//                    .categoryResList()
                    .build();
        }).collect(Collectors.toList());
    }

    @Transactional
    public void addMedicalCheckupOption(Long hospitalId, List<MedicalCheckupOptionReq> medicalCheckupOptionReqList) {
        var hospital = hospitalRepository.findById(hospitalId).get();
        List<Long> ids = medicalCheckupOptionReqList.stream().map(MedicalCheckupOptionReq::getCheckupItemId).collect(Collectors.toList());
        medicalCheckupItemRepo.findAllById(ids);
        var medicalCheckupOptionals = medicalCheckupOptionReqList.stream().map(medicalCheckupOptionReq -> {
            var medicalCheckupItem= medicalCheckupItemRepo.findById(medicalCheckupOptionReq.getCheckupItemId()).get();
            return MedicalCheckupOptional.builder()
                    .hospital(hospital)
                    .medicalCheckupItem(medicalCheckupItem)
                    .price(medicalCheckupOptionReq.getPrice())
                    .build();
        }).collect(Collectors.toList());
        medicalCheckupOptionalRepo.saveAll(medicalCheckupOptionals);
    }


}
