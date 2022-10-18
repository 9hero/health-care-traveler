package com.healthtrip.travelcare.repository.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class InquiryRequest {

    private String title;
    private String question;

}
