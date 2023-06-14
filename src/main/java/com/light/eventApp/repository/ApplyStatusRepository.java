package com.light.eventApp.repository;

import com.light.eventApp.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ApplyStatusRepository extends JpaRepository<ApplyStatus, UserEvent> {

    @Modifying
    @Query(value = "insert into user_event (user_id, event_id, status) VALUES (:userId,:id,:status)", nativeQuery = true)
    void save(@Param("userId") Long userId, @Param("id") Long id, @Param("status")CurrentStatus status);

    @Query("select a from ApplyStatus a where a.id.userId=:userId and a.id.eventId=:eventId")
    Optional<ApplyStatus> getById(@Param("userId") Long userId, @Param("eventId") Long eventId);
}
