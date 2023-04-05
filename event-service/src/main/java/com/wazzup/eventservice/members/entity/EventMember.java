package com.wazzup.eventservice.members.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "EVENT_MEMBER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_MEMBER_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "EVENT_ID")
    private Long eventId;

    @Column(name = "ACCEPTED")
    private boolean accepted = false;

    @Column(name = "BLOCKED")
    private boolean blocked = false;
}
