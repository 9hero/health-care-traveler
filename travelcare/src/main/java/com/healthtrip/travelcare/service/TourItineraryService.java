package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.entity.tour.tour_package.TourItinerary;
import com.healthtrip.travelcare.entity.tour.tour_package.TourItineraryElement;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPlaceList;
import com.healthtrip.travelcare.repository.dto.request.TourItineraryElementRequest;
import com.healthtrip.travelcare.repository.dto.request.TourItineraryRequest;
import com.healthtrip.travelcare.repository.dto.response.TourItineraryElementResponse;
import com.healthtrip.travelcare.repository.dto.response.TourItineraryResponse;
import com.healthtrip.travelcare.repository.tour.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourItineraryService {

    private final TourPackageRepository tourPackageRepository;

    private final TourItineraryRepository tourItineraryRepository;
    private final TourItineraryElementRepository elementRepository;
    private final TourPlaceRepository tourPlaceRepository;
    private final TourPlaceListRepository tourPlaceListRepository;

    @Transactional(readOnly = true)
    public List<TourItineraryResponse.WithElements> getTourItineraries(Long tourPackageId) {
        List<TourItineraryResponse.WithElements> itineraryList = new ArrayList<>();

        List<TourItinerary> tourItineraryList = tourItineraryRepository.findWithElements(tourPackageId);
        tourItineraryList.forEach(tourItinerary -> {
            var tourItineraryElementDtoList= tourItinerary.getItineraryElements().stream().map(
                    tourItineraryElement -> TourItineraryElementResponse.builder()
                            .sequence(tourItineraryElement.getSequence())
                            .title(tourItineraryElement.getTitle())
                            .elementType(tourItineraryElement.getElementType())
                            .build()
            ).collect(Collectors.toList());
            itineraryList.add(TourItineraryResponse.WithElements.builder()
                            .tourItineraryResponse(TourItineraryResponse.toResponse(tourItinerary))
                            .tourItineraryElementResponses(tourItineraryElementDtoList)
                    .build());
        });
        return itineraryList;
    }


    @Transactional
    public void addTourItinerariesWithElements(Long tourPackageId, List<TourItineraryRequest.WithElementAndPlaceList> itineraryAndElements) {
        var tourPackage = tourPackageRepository.getById(tourPackageId);
        // 요청
        var tourItineraries = itineraryAndElements.stream().map(request -> {
            var itineraryRequest = request.getTourItineraryRequest();
            var tourItineraryElements = request.getTourItineraryElements();
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
            tourItineraryElements.forEach(addPlaceList -> {
                // 요소 요청으로 일정요소 Entity 생성 후 일정에 추가

                // 일정요소데이터
                var elementData = addPlaceList.getElementRequest();

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

    @Transactional
    public void addTourItinerary(Long tourPackageId, List<TourItineraryRequest> tourItineraryRequests) {
        var tourPackage = tourPackageRepository.getById(tourPackageId);
        var tourItineraryList = tourItineraryRequests.stream().map(itineraryRequest -> {
            return TourItinerary.builder()
                    .tourPackage(tourPackage)
                    .day(itineraryRequest.getDay())
                    .accommodation(itineraryRequest.getAccommodation())
                    .details(itineraryRequest.getDetails())
                    .notice(itineraryRequest.getNotice())
                    .location(itineraryRequest.getLocation())
                    .specificLocations(itineraryRequest.getSpecificLocations())
                    .build();
        }).collect(Collectors.toList());
        tourItineraryRepository.saveAll(tourItineraryList);
    }


    @Transactional
    public void addTourItineraryElement(Long itineraryId, List<TourItineraryElementRequest> request) {
        var tourItinerary= tourItineraryRepository.getById(itineraryId);
        elementRepository.saveAll(
        request.stream().map(elementRequest -> {
            return TourItineraryElement.builder()
                    .elementType(elementRequest.getElementType())
                    .sequence(elementRequest.getSequence())
                    .tourItinerary(tourItinerary)
                    .title(elementRequest.getTitle())
                    .build();
        }).collect(Collectors.toList())
        );
    }

    @Transactional
    public void modifyTourItineraryElement(Long elementId, TourItineraryElementRequest request) {
        var element= elementRepository.getById(elementId);
        element.updateElement(request);
    }
}
