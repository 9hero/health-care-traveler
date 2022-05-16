package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.domain.entity.Account;
import com.healthtrip.travelcare.domain.entity.TripPackage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "비활성 TripPackageRequestDto")
public class TripPackageRequestDto {

    private Long AccountId;

    private String description;

    private BigDecimal price;

    private String type;

    public TripPackage toEntity(TripPackageRequestDto dto) {
        return TripPackage.builder()
                .description(dto.description)
                .price(dto.getPrice())
                .type(dto.getType())
                .build();

    }
}
