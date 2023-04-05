package com.wazzup.eventservice.ticket.controller;

import com.wazzup.eventservice.ticket.dto.BuyTicketDTO;
import com.wazzup.eventservice.ticket.service.TicketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "QrCode Managment")
@RestController
@RequestMapping("/api/ticket")
@AllArgsConstructor
public class TicketController {

   private final TicketService ticketService;

    @PostMapping("")
    public void createTicket(@RequestBody BuyTicketDTO dto, HttpServletRequest request) {
         ticketService.buyTicket(dto, request);
    }
}
