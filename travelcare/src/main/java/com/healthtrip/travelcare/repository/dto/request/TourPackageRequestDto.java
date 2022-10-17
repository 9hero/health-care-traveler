package com.healthtrip.travelcare.repository.dto.request;

import com.healthtrip.travelcare.entity.tour.tour_package.TourPackage;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackagePrices;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "패키지 관련 요청(등록,수정,삭제 등)")
public class TourPackageRequestDto {

    private String title;
    private String description;
    private String standardOffer;
    private String nonOffer;
    private String notice;
    private TourPackagePrices price;

    @Schema(description = "메인 이미지(썸네일)")
    private MultipartFile mainImage;

    @Schema(description = "투어 이미지 파일(메인이미지 제외)")
    private List<MultipartFile> packageImages;

    public TourPackage toEntity(TourPackageRequestDto dto) {
        return TourPackage.builder()
                .title(dto.title)
                .description(dto.description)
                .standardOffer(dto.standardOffer)
                .nonOffer(dto.nonOffer)
                .prices(dto.price)
                .notice(dto.notice)
//                .mainImage()
                .build();
    }
}
