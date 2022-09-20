package com.healthtrip.travelcare.integration;

import com.healthtrip.travelcare.annotation.IntegrationTestController;
import com.healthtrip.travelcare.entity.tour.tour_package.TourPackageFile;
import com.healthtrip.travelcare.repository.account.AccountsRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageFileRepository;
import com.healthtrip.travelcare.repository.tour.TourPackageRepository;
import com.healthtrip.travelcare.test_common.EntityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTestController
class TourPackageFileControllerITest {
    private final String domain = "/tour/package/file";

    @Autowired
    private MockMvc mockMvc;

    private TourPackageFile tourPackageFile;

    @Autowired
    TourPackageFileRepository tourPackageFileRepository;
    @Autowired
    TourPackageRepository tourPackageRepository;

    @Autowired
    AccountsRepository accountsRepository;
    @BeforeEach
    void setUp() {
        tourPackageFileRepository.deleteAllInBatch();
        tourPackageRepository.deleteAllInBatch();
        accountsRepository.deleteAllInBatch();
        tourPackageFile = new EntityProvider().getTourPackageFile();
    }

    @Test
    void givenTourPackageAndFile_whenGetImages_thenReturnTourImageList() {

        // given
        tourPackageFileRepository.save(tourPackageFile);
        Long packageId = tourPackageFile.getTourPackage().getId();
        // when
        try {
            ResultActions resultActions = mockMvc.perform(get("/api/"+domain+"/images")
                    .queryParam("tourPackageId",packageId.toString()));
        // then
            resultActions.andDo(print()).andExpect(status().isOk())
                    .andExpect(result -> System.out.println("겟 컨텐츠" + result.getResponse().getContentAsString()))
                    .andExpect(jsonPath("$.[0]url",is(tourPackageFile.getUrl())));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}