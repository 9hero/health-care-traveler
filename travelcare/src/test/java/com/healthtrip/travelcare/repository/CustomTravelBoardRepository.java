package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.CustomTravelBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomTravelBoardRepository extends JpaRepository<CustomTravelBoard,Long> {
}
