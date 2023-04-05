package com.wazzup.eventservice.event.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "EVENT_TYPE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_TYPE_ID")
    private Long id;

    @Column(name = "EVENT_TYPE_NAME")
    private String name;

    @ManyToMany(mappedBy = "eventTypes")
    @JsonIgnore
    private Set<Event> users;
}
