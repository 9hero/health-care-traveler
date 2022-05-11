package com.healthtrip.travelcare.service;

import com.healthtrip.travelcare.repository.NoticeBoardRepository;
import com.healthtrip.travelcare.repository.dto.response.NoticeBoardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeBoardService {

    @Autowired
    NoticeBoardRepository noticeBoardRepository;

    @Transactional
    public List<NoticeBoardResponseDto.mainPageNoticeBoard> AllPosts() {
        // 프론트에 보낼 posts dto 초기화
        var posts = new ArrayList<NoticeBoardResponseDto.mainPageNoticeBoard>();


        noticeBoardRepository.findAll().forEach(noticeBoard -> {
            // entity -> dto
            var post = NoticeBoardResponseDto.mainPageNoticeBoard.builder()
                    .id(noticeBoard.getId())
                    .title(noticeBoard.getTitle())
                    .createdAt(noticeBoard.getCreatedAt().toLocalDate())
                    .build();
            //dto insert into list
            posts.add(post);
        });

        return posts;
    }

}
