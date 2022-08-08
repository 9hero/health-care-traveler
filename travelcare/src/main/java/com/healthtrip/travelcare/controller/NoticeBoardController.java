package com.healthtrip.travelcare.controller;

import com.healthtrip.travelcare.repository.dto.request.NoticeBoardRequest;
import com.healthtrip.travelcare.repository.dto.response.NoticeBoardResponse;
import com.healthtrip.travelcare.service.NoticeBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "공지사항 API",description = "공지사항을 관리하는 API")
public class NoticeBoardController {
    private final String domain = "/notice-board";
    private final String adminApi = "/admin"+domain;
    NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }
    @SecurityRequirement(name = "no")
    @Operation(summary = "메인 페이지에 들어갈 공지사항 리스트")
    @GetMapping(domain)//List<NoticeBoardResponse.mainPageNoticeBoard>
    public ResponseEntity<List<NoticeBoardResponse.mainPageNoticeBoard>> AllPosts(){
        return noticeBoardService.AllPosts();
    }
    @SecurityRequirement(name = "no")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "성공"),
            @ApiResponse(responseCode = "404",description = "공지사항을 찾을 수 없음",content = @Content(examples = @ExampleObject))
    })
    @Operation(summary = "공지사항 자세히 보기(제목클릭) 현재 id:1,2")
    @GetMapping(domain+"/{id}")
    public ResponseEntity<NoticeBoardResponse.NoticeBoardDetails> postDetails(@PathVariable Long id){
        return noticeBoardService.postDetails(id);
    }


    // <관리자 API>
    @Operation(summary = "공지사항 등록")
    @PostMapping(adminApi)
    public ResponseEntity addPost(@RequestBody NoticeBoardRequest.AddPost request) {
        return noticeBoardService.addPost(request);
    }

    // 업데이트 방식 선택바람 1. 덮어씌우기(리액트에서 작업) 2. 부분변경
    @Operation(summary = "공지사항 변경")
    @PutMapping(adminApi)
    public ResponseEntity updatePost(@RequestBody NoticeBoardRequest.Update request) {
        return noticeBoardService.updatePost(request);
    }
    @Operation(summary = "공지 삭제")
    @DeleteMapping(adminApi+"{id}")
    public ResponseEntity deletePost(@PathVariable Long id) {
        return noticeBoardService.deletePost(id);
    }
}
