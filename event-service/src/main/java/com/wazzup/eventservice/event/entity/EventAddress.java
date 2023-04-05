package com.wazzup.eventservice.event.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "EVENT_ADDRESS")
public class EventAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ADDRESS_ID")
    private Long id;

    @Column(name = "CITY")
    private String city;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "LAT", columnDefinition = "Decimal(8,6)")
    private Double lat;
    @Column(name = "LNG", columnDefinition = "Decimal(8,6)")
    private Double lng;

    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "eventAddress")
    private Event event;
}
