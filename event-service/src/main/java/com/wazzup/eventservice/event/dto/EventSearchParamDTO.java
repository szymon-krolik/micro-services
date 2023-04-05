package com.wazzup.eventservice.event.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventSearchParamDTO {
    private String name;
    private String city;
    private List<Long> eventTypes;
    private Long owner;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
