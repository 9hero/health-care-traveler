package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.domain.entity.NoticeBoard;
import com.healthtrip.travelcare.repository.NoticeBoardRepository;
import com.healthtrip.travelcare.repository.dto.response.NoticeBoardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeBoardService {

    @Autowired
    NoticeBoardRepository noticeBoardRepository;

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
}
