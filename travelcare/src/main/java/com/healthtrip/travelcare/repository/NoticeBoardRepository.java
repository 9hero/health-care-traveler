package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard,Long> {
}
