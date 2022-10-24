package com.healthtrip.travelcare.repository;

import com.healthtrip.travelcare.entity.account.Tendency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TendencyRepository extends JpaRepository<Tendency,Long> {

    @Query("select t from Tendency t " +
            "where t.friendlinessLevel = :f " +
            "and t.opennessLevel = :o " +
            "and t.extroversionLevel = :e ")
    Tendency findByLevels(
            @Param("f") Tendency.ScoreLevel friendLevel,
            @Param("o") Tendency.ScoreLevel openLevel,
            @Param("e") Tendency.ScoreLevel extroversionLevel
            );
}
