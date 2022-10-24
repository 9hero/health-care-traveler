package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.entity.account.Tendency;
import com.healthtrip.travelcare.repository.dto.response.TendencyResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TendencyRequest {

    private String name;
    private String description;
    private MultipartFile multipartFile;
    private Tendency.ScoreLevel extroversionLevel;
    private Tendency.ScoreLevel opennessLevel;
    private Tendency.ScoreLevel friendlinessLevel;
}
