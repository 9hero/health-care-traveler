package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.service.NoticeBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureMockMvc
@SpringBootTest
class NoticeBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    NoticeBoardService noticeBoardService;

    @Test
    @Transactional
    void allPosts() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("http://localhost:8080/api/notice-board")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}