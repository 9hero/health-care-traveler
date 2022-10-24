package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.entity.GeneralFile;
import com.healthtrip.travelcare.entity.account.Tendency;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackageFile;
import com.healthtrip.travelcare.repository.TendencyRepository;
import com.healthtrip.travelcare.repository.dto.request.TendencyRequest;
import com.healthtrip.travelcare.repository.dto.response.TendencyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TendencyService {

    private final AwsS3Service awsS3Service;
    private final TendencyRepository tendencyRepository;

    @Transactional(readOnly = true)
    public List<TendencyResponse> findAll() {
        return tendencyRepository.findAll().stream().map(TendencyResponse::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public void addTendency(TendencyRequest tendencyRequest) {
        GeneralFile generalFile = null;
        if (tendencyRequest.getMultipartFile()!=null){
            var file = tendencyRequest.getMultipartFile();
            String IMAGE_DIRECTORY = "/images/tendency";
            String fileName = CommonUtils.buildFileName(Objects.requireNonNull(file.getOriginalFilename()), IMAGE_DIRECTORY);
            var url = awsS3Service.uploadS3File(tendencyRequest.getMultipartFile(),fileName);
            generalFile = GeneralFile.builder()
                    .url(url)
                    .fileSize(file.getSize())
                    .fileName(fileName)
                    .originalName(file.getOriginalFilename())
                    .build();
        }
        var tendency=Tendency.builder()
                .name(tendencyRequest.getName())
                .description(tendencyRequest.getDescription())
//                .imageFile(generalFile)
                .friendlinessLevel(tendencyRequest.getFriendlinessLevel())
                .extroversionLevel(tendencyRequest.getExtroversionLevel())
                .opennessLevel(tendencyRequest.getOpennessLevel())
                .build();
        if (generalFile != null) {
            tendency.setImageFile(generalFile);
        }
        tendencyRepository.save(tendency);
    }
}
