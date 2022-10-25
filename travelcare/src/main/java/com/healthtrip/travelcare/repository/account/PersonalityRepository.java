package com.healthtrip.travelcare.repository.account;

import com.healthtrip.travelcare.entity.account.Personality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonalityRepository extends JpaRepository<Personality,Long> {

    @Query("select t.id from AccountCommon ac " +
            "join ac.personality p " +
            "join p.tendency t " +
            "where ac.id = :id")
    Long findTendencyIdByAccountId(@Param("id") Long id);
}
