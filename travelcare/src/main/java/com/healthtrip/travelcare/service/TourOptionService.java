package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.entity.tour.reservation.TourOption;
import com.healthtrip.travelcare.repository.dto.request.ReservationTourOptionsRequest;
import com.healthtrip.travelcare.repository.dto.response.TourOptionsResponse;
import com.healthtrip.travelcare.repository.tour.TourOptionRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourOptionService {

    private final TourOptionRepository tourOptionRepository;
    private final TourPackageRepository tourPackageRepository;
    @Transactional(readOnly = true)
    public List<TourOptionsResponse> getOptions(Long tourPackageId) {
        var tourPackage = tourPackageRepository.getById(tourPackageId);
        return tourOptionRepository.findByTourPackage(tourPackage).stream().map(tourOption -> {
            return TourOptionsResponse.builder()
                    .optionId(tourOption.getId())
                    .optionName(tourOption.getOptionName()).build();
        }).collect(Collectors.toList());
    }
}
