package com.wazzup.eventservice.qrcode.entity;


import com.wazzup.eventservice.event.entity.Event;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "QR_CODE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QR_CODE_ID")
    private Long id;

    @Lob
    @Column(name = "CONTENT")
    byte[] content;

    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "qrCodeContent")
    private Event event;
}
