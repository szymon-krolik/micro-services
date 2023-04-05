package com.wazzup.eventservice.event.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wazzup.eventservice.qrcode.entity.QrCode;
import com.wazzup.eventservice.ticket.entity.Ticket;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "EVENT")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "EVENT_END_DATE")
    private LocalDateTime endDate;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "MAX_MEMBERS")
    private Integer maxMembers;

    @Column(name = "QR_CODE")
    private boolean qrCode;

    @Column(name = "HISTORY")
    private boolean history;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "EVENT_TYPES",
            joinColumns = @JoinColumn(name = "EVENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "EVENT_TYPE_ID"))
    private List<EventType> eventTypes;

    @JsonIgnore
    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets;

    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EVENT_ADDRESS_ID", referencedColumnName = "EVENT_ADDRESS_ID")
    private EventAddress eventAddress;

    @CreationTimestamp
    @Column(updatable = false, name = "CREATED_AT")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "QR_CODE_ID", referencedColumnName = "QR_CODE_ID")
    private QrCode qrCodeContent;



}
