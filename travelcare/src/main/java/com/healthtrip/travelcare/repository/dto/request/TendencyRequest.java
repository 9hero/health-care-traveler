package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.entity.account.Tendency;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TendencyRequest {

    private String name;
    private String description;
    private MultipartFile imageFile;
    @Schema(description = "외향성")
    private Tendency.ScoreLevel extroversionLevel;
    @Schema(example = "L",description = "개방성")
    private Tendency.ScoreLevel opennessLevel;
    @Schema(description = "우호성")
    private Tendency.ScoreLevel friendlinessLevel;
}
