package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.repository.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepository extends JpaRepository<TestEntity,Long> {
}
