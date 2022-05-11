package com.healthtrip.travelcare.repository.dto.response;


import com.healthtrip.travelcare.domain.entity.TripPackage;
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
public class TripPackageDetailResponseDto {

    private Long id;

    private String description;

    private BigDecimal price;

    private String type;

    private List<ReservationDateResponseDto> reservationDateList;

    public TripPackageDetailResponseDto entityToResponseBasic(TripPackage tripPackage) {
        return
        TripPackageDetailResponseDto.builder()
                .id(tripPackage.getId())
                .description(tripPackage.getDescription())
                .price(tripPackage.getPrice())
                .type(tripPackage.getType())
                .build();
    }
}
