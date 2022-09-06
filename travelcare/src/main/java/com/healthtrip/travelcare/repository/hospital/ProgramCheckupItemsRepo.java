package com.healthtrip.travelcare.repository.hospital;

import com.healthtrip.travelcare.entity.hospital.ProgramCheckupItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramCheckupItemsRepo extends JpaRepository<ProgramCheckupItem,Long> {
}
