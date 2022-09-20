package com.healthtrip.travelcare.entity.tour.tour_package;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class TourPackagePrices {

    private BigDecimal adultPrice;
    private BigDecimal childPrice;
    private BigDecimal infantPrice;
}
