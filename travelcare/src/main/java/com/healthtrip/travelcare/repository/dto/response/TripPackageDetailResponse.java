package com.healthtrip.travelcare.repository.dto.response;


import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripPackageDetailResponse {

    private Long id;

    private String description;

    private BigDecimal price;

    private String type;

    private List<ReservationDateResponse> reservationDateList;

    public TripPackageDetailResponse entityToResponseBasic(TourPackage tourPackage) {
        return
        TripPackageDetailResponse.builder()
                .id(tourPackage.getId())
                .description(tourPackage.getDescription())
//                .price(tourPackage.getPrice())
//                .type(tourPackage.getPlaceShowType())
                .build();
    }
}
