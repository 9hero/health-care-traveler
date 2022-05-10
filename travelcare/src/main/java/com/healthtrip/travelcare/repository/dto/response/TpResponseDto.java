package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.domain.entity.TripPackage;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class TpResponseDto {

    private Long id;

    private String description;

    private BigDecimal price;

    private String type;

    private TripPackageFileResponseDto tripPackageFileResponseDto;

    public static TpResponseDto entityToDto(TripPackage tripPackage){
        return TpResponseDto.builder()
                .id(tripPackage.getId())
                .description(tripPackage.getDescription())
                .price(tripPackage.getPrice())
                .type(tripPackage.getType())
                .build();
    }
}
