package com.healthtrip.travelcare.repository.account;

import com.healthtrip.travelcare.entity.account.AccountCommon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountCommonRepository extends JpaRepository<AccountCommon,Long> {

    @Query("select ac from AccountCommon ac " +
            "left join fetch ac.personality p " +
            "join fetch p.tendency " +
            "where ac.userId = :userId")
    AccountCommon findWithPersonality(@Param("userId") Long userId);
}
