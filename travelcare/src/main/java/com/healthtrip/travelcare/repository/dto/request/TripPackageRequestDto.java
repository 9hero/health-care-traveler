package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "패키지 관련 요청(등록,수정,삭제 등)")
public class TripPackageRequestDto {

    private Long AccountId;

    private String title;

    private String description;

    private BigDecimal price;

    private String type;

    @Schema(description = "이미지 파일을 보내주세요 여러개도 가능합니다.")
    private List<MultipartFile> multipartFiles;

    public TourPackage toEntity(TripPackageRequestDto dto) {
        return TourPackage.builder()
                .description(dto.getDescription())
                .title(dto.getTitle())


                .build();
    }
}
