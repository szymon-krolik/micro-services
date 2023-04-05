package com.wazzup.eventservice.event.dto;

import lombok.Data;

@Data
public class EventAddressDTO {
    private String city;
    private String address;
    private Double lng;
    private Double lat;
}
