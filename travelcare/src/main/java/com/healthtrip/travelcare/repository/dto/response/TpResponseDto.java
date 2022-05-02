package com.healthtrip.travelcare.repository.dto.response;

import com.healthtrip.travelcare.domain.entity.TripPackage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@ToString
public class TpResponseDto {

    @Builder
    public TpResponseDto(Long id, String description, BigDecimal price, String type) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    private Long id;

    private String description;

    private BigDecimal price;

    private String type;



    public static TpResponseDto entityToDto(TripPackage tripPackage){
        return TpResponseDto.builder()
                .id(tripPackage.getId())
                .description(tripPackage.getDescription())
                .price(tripPackage.getPrice())
                .type(tripPackage.getType())
                .build();
    }
}
