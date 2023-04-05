package com.wazzup.eventservice.event.repository;

import com.wazzup.eventservice.event.entity.EventAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventAddressRepository extends JpaRepository<EventAddress, Long> {
}
