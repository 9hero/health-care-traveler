package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.NoticeBoard;
import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.NoticeBoardRepository;
import com.healthtrip.travelcare.repository.dto.request.NoticeBoardRequest;
import com.healthtrip.travelcare.repository.dto.response.NoticeBoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;

    private final AccountsRepository accountsRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<List<NoticeBoardResponse.mainPageNoticeBoard>> AllPosts() {
        // response dto 생성
        var posts = new ArrayList<NoticeBoardResponse.mainPageNoticeBoard>();

        noticeBoardRepository.findAll().forEach(noticeBoard -> {
            // entity -> dto
            var post = NoticeBoardResponse
                    .mainPageNoticeBoard.builder()
                    .id(noticeBoard.getId())
                    .title(noticeBoard.getTitle())
                    .createdAt(noticeBoard.getCreatedAt().toLocalDate())
                    .build();
            //dto insert into list
            posts.add(post);
        });

        return ResponseEntity.ok(posts);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<NoticeBoardResponse.NoticeBoardDetails> postDetails(Long id) {
        Optional<NoticeBoard> optionalNoticeBoard= noticeBoardRepository.findById(id);
        if(optionalNoticeBoard.isPresent()){
            var noticeBoard = optionalNoticeBoard.get();
            var noticeBoardDetailsDto = NoticeBoardResponse
                    .NoticeBoardDetails.builder()
                    .id(noticeBoard.getId())
                    .title(noticeBoard.getTitle())
                    .announcement(noticeBoard.getAnnouncement())
                    .createdAt(noticeBoard.getCreatedAt().toLocalDate())
                    .build();
            return ResponseEntity.ok(noticeBoardDetailsDto);
        }else
            return ResponseEntity.notFound().build();
    }

    @Transactional // 미완성
    public ResponseEntity updatePost(NoticeBoardRequest.Update updateRequest) {
        var optionalNoticeBoard= noticeBoardRepository.findById(updateRequest.getNoticePostId());
        if(optionalNoticeBoard.isPresent()){
            var noticeBoard = optionalNoticeBoard.get();
            noticeBoard.update(updateRequest.getTitle(),updateRequest.getAnnouncement());
            return ResponseEntity.ok("등록완료");
        }else {
            return ResponseEntity.badRequest().body("등록실패");
        }

    }

    @Transactional
    public ResponseEntity addPost(NoticeBoardRequest.AddPost addRequest) {
        // 유저검증 필요
        Long userId = addRequest.getUserId();
        var account = accountsRepository.getById(userId);

        NoticeBoard noticeBoard = NoticeBoard.builder()
                .title(addRequest.getTitle())
                .account(account)
                .announcement(addRequest.getAnnouncement())
                .build();
        noticeBoardRepository.save(noticeBoard);
        return ResponseEntity.ok("성공");
    }

    @Transactional
    public ResponseEntity deletePost(Long id) {
        noticeBoardRepository.deleteById(id);
        return ResponseEntity.ok("삭제완료");
    }
}
