package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.repository.dto.response.TourOptionsResponse;
import com.healthtrip.travelcare.repository.tour.TourOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourOptionService {

    private final TourOptionRepository tourOptionRepository;
    @Transactional(readOnly = true)
    public List<TourOptionsResponse> getOptions() {
        return tourOptionRepository.findAll().stream().map(tourOption -> {
            return TourOptionsResponse.builder()
                    .optionId(tourOption.getId())
                    .optionName(tourOption.getOptionName()).build();
        }).collect(Collectors.toList());
    }
}
