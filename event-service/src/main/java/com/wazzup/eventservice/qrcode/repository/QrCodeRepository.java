package com.wazzup.eventservice.qrcode.repository;

import com.wazzup.eventservice.qrcode.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, Long> {

    @Query("SELECT q.content FROM QrCode q WHERE q.event.id = :id")
    Optional<byte[]> findQrCodeContentByEventId(@Param("id") Long id);
}
