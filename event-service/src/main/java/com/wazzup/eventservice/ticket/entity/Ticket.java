package com.wazzup.eventservice.ticket.entity;

import com.wazzup.eventservice.event.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TICKET")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TICKET_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name="EVENT_ID", nullable=false)
    private Event event;
}
