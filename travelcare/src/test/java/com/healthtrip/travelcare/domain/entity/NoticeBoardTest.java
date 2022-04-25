package com.healthtrip.travelcare.domain.entity;

import com.healthtrip.travelcare.repository.AccountsRepository;
import com.healthtrip.travelcare.repository.NoticeBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeBoardTest {

    @Autowired
    NoticeBoardRepository repository;
    @Autowired
    AccountsRepository accountsRepository;

    @Test
    @Transactional
    void boardTest(){

        Account account = accountsRepository.findAll().get(0);
        NoticeBoard noticeBoard = NoticeBoard.builder()
                .announcement("공지사항입니다.")
                .account(account)
                .title("테스트 제목")
                .build();

        repository.save(noticeBoard);
        repository.flush();

        System.out.println(repository.findAll().get(0));

    }
}