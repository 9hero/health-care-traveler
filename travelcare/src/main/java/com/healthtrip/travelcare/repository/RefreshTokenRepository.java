package com.healthtrip.travelcare.repository;


import com.healthtrip.travelcare.domain.entity.account.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    @Query("select rt from RefreshToken rt where rt.id = :id and rt.account.id =:userId")
    RefreshToken findByIdAndAccountId(Long id,Long userId);
}
