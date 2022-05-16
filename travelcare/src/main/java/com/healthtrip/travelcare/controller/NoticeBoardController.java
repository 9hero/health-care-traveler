package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.response.NoticeBoardResponse;
import com.healthtrip.travelcare.service.NoticeBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notice-board")
@Tag(name = "공지사항 API",description = "공지사항을 불러와요")
public class NoticeBoardController {

    NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    @Operation(summary = "메인 페이지에 들어갈 공지사항 리스트")
    @GetMapping("")//List<NoticeBoardResponse.mainPageNoticeBoard>
    public ResponseEntity<List<NoticeBoardResponse.mainPageNoticeBoard>> AllPosts(){
        return noticeBoardService.AllPosts();
    }

    @Operation(summary = "공지사항 자세히 보기(제목클릭) 현재 id:1,2")
    @GetMapping("/{id}")
    public ResponseEntity<NoticeBoardResponse.NoticeBoardDetails> postDetails(@PathVariable Long id){
        return noticeBoardService.postDetails(id);
    }
}
