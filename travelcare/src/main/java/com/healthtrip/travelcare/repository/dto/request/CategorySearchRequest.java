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
        private List<Long> categoryId;
        private List<Long> programCategoryIds;
    }

}
