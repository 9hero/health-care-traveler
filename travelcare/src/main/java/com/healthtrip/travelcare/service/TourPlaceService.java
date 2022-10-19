package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.common.CommonUtils;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPlace;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPlaceImage;
import com.healthtrip.travelcare.repository.dto.request.TourPlaceRequest;
import com.healthtrip.travelcare.repository.tour.TourPlaceImageRepository;
import com.healthtrip.travelcare.repository.tour.TourPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TourPlaceService {

    private final TourPlaceRepository tourPlaceRepository;
    private final TourPlaceImageRepository tourPlaceImageRepository;

    private final AwsS3Service awsS3Service;
    private static final String IMAGE_DIRECTORY = "images/tour/place"; // 패키지 이미지폴더로 이동예정


    @Transactional
    public void addPlace(TourPlaceRequest.WithPlaceImage tourPlaceRequest) {
        var placeRequest = tourPlaceRequest.getTourPlaceRequest();
        var images = tourPlaceRequest.getImages();

        var tourPlace = TourPlace.builder()
                .description(placeRequest.getDescription())
                .placeName(placeRequest.getPlaceName())
                .summery(placeRequest.getSummery())
                .build();

        if (images != null){
            for (MultipartFile image : images) {
            String fileName = CommonUtils.buildFileName(Objects.requireNonNull(image.getOriginalFilename()), IMAGE_DIRECTORY);
            String url = awsS3Service.uploadS3File(image,fileName);
                var tourPlaceImage = TourPlaceImage.builder()
                        .tourPlace(tourPlace)
                        .fileName(fileName)
                        .fileSize(image.getSize())
                        .originalName(image.getOriginalFilename())
                        .url(url)
                        .build();
                tourPlace.addTourPlaceImage(tourPlaceImage);
            }
        }

        tourPlaceRepository.save(tourPlace);
    }

    @Transactional
    public void modifyPlace(Long placeId, TourPlaceRequest tourPlaceRequest) {
        var tourPlace = tourPlaceRepository.getById(placeId);
        tourPlace.update(tourPlaceRequest);
    }
}
