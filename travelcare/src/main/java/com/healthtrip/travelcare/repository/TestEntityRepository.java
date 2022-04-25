package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.domain.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepository extends JpaRepository<TestEntity,Long> {
}
