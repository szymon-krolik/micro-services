package com.wazzup.eventservice.event.repository;

import com.wazzup.eventservice.event.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Long> {
    @Query("SELECT et FROM EventType et WHERE et.id in :eventTypeId")
    List<EventType> findAllById(@Param("eventTypeId") List<Long> eventTypeId);
}
