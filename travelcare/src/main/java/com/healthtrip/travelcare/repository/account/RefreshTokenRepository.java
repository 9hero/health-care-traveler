package com.healthtrip.travelcare.repository.account;


import com.healthtrip.travelcare.entity.account.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
//    @Query("select rt from RefreshToken rt where rt.id = :id and rt.account.id =:userId")
    RefreshToken findByIdAndAccountId(Long id,Long userId);
}
