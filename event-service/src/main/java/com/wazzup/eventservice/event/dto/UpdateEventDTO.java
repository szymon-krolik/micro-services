package com.wazzup.eventservice.event.dto;

import lombok.Data;

@Data
public class UpdateEventDTO {
    CreateEventDTO eventInformation;
    Long eventId;
}
