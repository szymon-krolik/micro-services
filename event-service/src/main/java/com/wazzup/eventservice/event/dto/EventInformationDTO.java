package com.wazzup.eventservice.event.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventInformationDTO {
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> eventTypes;
    private EventAddressDTO eventAddress;
}
