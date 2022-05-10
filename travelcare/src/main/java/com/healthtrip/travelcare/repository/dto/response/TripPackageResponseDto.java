package com.healthtrip.travelcare.repository.dto.response;


import lombok.*;

import java.math.BigDecimal;


public class TripPackageResponseDto {

    @Data
    @Builder
    public static class mainPagePack {
       private Long id;

       private String title;

       private BigDecimal price;

       private TripPackageFileResponseDto.mainPagePackImage thumbnail;
    }
}
