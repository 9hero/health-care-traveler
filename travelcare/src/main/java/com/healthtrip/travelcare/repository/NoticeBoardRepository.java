package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard,Long> {
}
