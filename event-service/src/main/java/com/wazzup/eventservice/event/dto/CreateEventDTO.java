package com.wazzup.eventservice.event.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class CreateEventDTO {
    @NotNull(message = "Event name should not be null")
    @Size(min = 2, max = 15)
    private String name;
    @NotNull(message = "Event description should not be null")
    @Size(min = 2, max = 200)
    private String description;
    @NotNull(message = "Date should not be null")
    private LocalDateTime startDate;
    @NotNull(message = "Date should not be null")
    private LocalDateTime endDate;
    @NotNull(message = "Max members should not be null")
    private Integer maxMembers;
    private boolean qrCode;
    @NotNull(message = "Event types should not be null")
    private Set<Long> eventTypes;

    @NotNull(message = "Address shoudl not be null")
    private EventAddressDTO eventAddress;

}
