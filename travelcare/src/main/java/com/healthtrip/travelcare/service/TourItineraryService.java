package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.entity.tour.tour_package.TourItinerary;
import com.healthtrip.travelcare.entity.tour.tour_package.TourItineraryElement;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPlaceList;
import com.healthtrip.travelcare.repository.dto.response.TourItineraryDto;
import com.healthtrip.travelcare.repository.dto.response.TourItineraryElementDto;
import com.healthtrip.travelcare.repository.tour.TourItineraryRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageRepository;
import com.healthtrip.travelcare.repository.tour.TourPlaceListRepository;
import com.healthtrip.travelcare.repository.tour.TourPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourItineraryService {

    private final TourItineraryRepository tourItineraryRepository;

    private final TourPlaceListRepository tourPlaceListRepository;
    private final TourPlaceRepository tourPlaceRepository;
    private final TourPackageRepository tourPackageRepository;

    @Transactional(readOnly = true)
    public List<TourItineraryDto> getTourItineraries(Long tourPackageId) {
        List<TourItineraryDto> itineraryList = new ArrayList<>();

        List<TourItinerary> tourItineraryList = tourItineraryRepository.findWithElements(tourPackageId);
        tourItineraryList.forEach(tourItinerary -> {
            var tourItineraryElementDtoList= tourItinerary.getItineraryElements().stream().map(
                    tourItineraryElement -> TourItineraryElementDto.builder()
                            .sequence(tourItineraryElement.getSequence())
                            .title(tourItineraryElement.getTitle())
                            .elementType(tourItineraryElement.getElementType())
                            .build()
            ).collect(Collectors.toList());
            itineraryList.add(TourItineraryDto.builder()
                            .itineraryId(tourItinerary.getId())
                            .day(tourItinerary.getDay())
                            .accommodation(tourItinerary.getAccommodation())
                            .details(tourItinerary.getDetails())
                            .notice(tourItinerary.getNotice())
                            .location(tourItinerary.getLocation())
                            .specificLocations(tourItinerary.getSpecificLocations())
                            .tourItineraryElements(tourItineraryElementDtoList)
                    .build());
        });
        return itineraryList;
    }


    @Transactional
    public void addTourItineraries(Long tourPackageId, List<TourItineraryDto.AddItineraryRequest> addItineraryRequest) {
        var tourPackage = tourPackageRepository.getById(tourPackageId);
        // 요청
        var tourItineraries = addItineraryRequest.stream().map(itineraryRequest -> {
            // 일정 request 받기
            var tourItinerary= TourItinerary.builder()
                    .tourPackage(tourPackage)
                    .day(itineraryRequest.getDay())
                    .accommodation(itineraryRequest.getAccommodation())
                    .details(itineraryRequest.getDetails())
                    .notice(itineraryRequest.getNotice())
                    .location(itineraryRequest.getLocation())
                    .specificLocations(itineraryRequest.getSpecificLocations())
                    .build();

            // 일정의 요소목록 받기
            itineraryRequest.getTourItineraryElements().forEach(addPlaceList -> {
                // 요소 요청으로 일정요소 Entity 생성 후 일정에 추가

                // 일정요소데이터
                var elementData = addPlaceList.getTourItineraryElementDto();

                // 일정요소의 장소목록 데이터
                var tourPlaceListData = addPlaceList.getTourPlaceListRequestList();

                // 일정요소 데이터로 엔티티 생성
                var tourItineraryElement=TourItineraryElement.builder()
                        .tourItinerary(tourItinerary)
                        .title(elementData.getTitle())
                        .sequence(elementData.getSequence())
                        .elementType(elementData.getElementType())
                        .build();
                // 일정에 일정 요소 추가
                tourItinerary.addItineraryElement(tourItineraryElement);

                // 일정요소의 장소목록이 있다면
                if (tourPlaceListData != null){
                    // 장소목록 데이터 토대로
                    tourPlaceListData.forEach(tourPlaceListRequest -> {
                        // 요소에 장소 추가
                    tourItineraryElement.addTourPlaceList(
                            // 장소목록 엔티티 생성
                        TourPlaceList.builder()
                                .tourPlace(tourPlaceRepository.getById(tourPlaceListRequest.getPlaceId()))
                                .tourItineraryElement(tourItineraryElement)
                                .placeShowType(tourPlaceListRequest.getPlaceShowType())
                                .build()
                    );
                    });
                }

            });
            return tourItinerary;
        }).collect(Collectors.toList());
        tourItineraryRepository.saveAll(tourItineraries);
    }
}
