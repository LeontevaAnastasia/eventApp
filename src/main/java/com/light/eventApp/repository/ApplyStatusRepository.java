package com.light.eventApp.repository;

import com.light.eventApp.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ApplyStatusRepository extends JpaRepository<ApplyStatus, UserEvent> {

    @Query("select a from ApplyStatus a where a.id.userId=:userId and a.id.eventId=:eventId")
    Optional<ApplyStatus> getById(@Param("userId") Long userId, @Param("eventId") Long eventId);

    @Query(" select u from User u join ApplyStatus a on a.id.userId=u.id where a.event.id=:id and a.currentStatus =:status")
    Optional<List<User>> getAllParticipants(@Param("id") Long id, @Param("status") CurrentStatus status);
}
