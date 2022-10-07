package com.healthtrip.travelcare.repository.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "범주로 검색요청")
public class CategorySearchRequest {
    private List<Long> categoryId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ModifyProgramCategory{
        @Schema(description = "추가할 범주 id")
        private List<Long> categoryId;
        @Schema(description = "삭제할 범주 id, 주의 프로그램 상세조회 해야지 얻을 수 있음")
        private List<Long> programCategoryIds;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AddProgramCategory{
        @Schema(description = "추가할 범주 id")
        private List<Long> categoryId;
    }
}
