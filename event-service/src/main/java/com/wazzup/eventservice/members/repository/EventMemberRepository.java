package com.wazzup.eventservice.members.repository;

import com.wazzup.eventservice.members.entity.EventMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventMemberRepository extends JpaRepository<EventMember, Long> {
    @Query("SELECT em FROM EventMember em WHERE em.eventId = :eventId AND em.userId = :userId")
    Optional<EventMember> findByEventIdAndUserId(@Param("eventId") Long eventId, @Param("userId") Long userId);
}
