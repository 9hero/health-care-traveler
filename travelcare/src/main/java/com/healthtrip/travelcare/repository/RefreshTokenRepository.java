package com.healthtrip.travelcare.repository;


import com.healthtrip.travelcare.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
}
