package com.healthtrip.travelcare.repository.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourPlaceRequest {

    private String placeName;
    private String summery;
    private String description;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WithPlaceImage{
        private TourPlaceRequest tourPlaceRequest;
        private List<MultipartFile> images;
//        private List<PlaceImageRequest> imageRequests;
    }
}
