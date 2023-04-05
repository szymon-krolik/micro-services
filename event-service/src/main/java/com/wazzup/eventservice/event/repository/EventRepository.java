package com.wazzup.eventservice.event.repository;

import com.wazzup.eventservice.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    @Query("SELECT e FROM Event e WHERE e.userId = :userId")
    List<Event> findAllByUserId(Long userId);

    @Query("SELECT e FROM Event e where e.id = :id")
    Optional<Event> findQrCodeByEventId(@Param("id") Long id);

    @Query("SELECT e FROM Event e where e.id = :id AND e.history <> false")
    Optional<Event> findByIdNotHistory(@Param("id") Long id);
}
