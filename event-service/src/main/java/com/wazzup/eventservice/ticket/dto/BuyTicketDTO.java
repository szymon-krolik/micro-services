package com.wazzup.eventservice.ticket.dto;

import lombok.Data;

@Data
public class BuyTicketDTO {
    private Long eventId;
    private int quantity;
}
