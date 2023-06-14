package com.light.eventApp.repository;

import com.light.eventApp.model.ApplyStatus;
import com.light.eventApp.model.Event;
import com.light.eventApp.model.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ApplyStatusRepository extends JpaRepository<ApplyStatus, UserEvent> {

    @Modifying
    @Query(value = "insert into user_event (user_id, event_id, status) VALUES (:userId,:id,'APPLY')", nativeQuery = true)
    void saveApplying( @Param("userId") Long userId, @Param("id") Long id);
}
