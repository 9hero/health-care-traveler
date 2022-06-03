package com.healthtrip.travelcare.repository.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "여행패키지 파일전송요청")
public class TripPackageFileRequest {

    private Long tripPackageId;
//    private Long fileId;
    @Schema(description = " <input type='file' name='files' multiple'> 를 사용해서 보내주세요 ")
    private List<MultipartFile> files;

}
