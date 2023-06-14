package com.light.eventApp.repository;

import com.light.eventApp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event e where e.creator.id=:userId")
    Optional<List<Event>> getAllByCreator(@Param("userId") Long userId);

    @Query("select a from ApplyStatus a where a.event.id=:id and a.currentStatus ='ACCEPTED'")
    Optional<List<Event>> getAllParticipants(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("delete from Event e where e.id=:id")
    Long delete(@Param("id") Long id);

}
