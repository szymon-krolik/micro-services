package com.wazzup.eventservice.ticket.service;

import com.wazzup.eventservice.event.client.UserServiceClient;
import com.wazzup.eventservice.event.dto.user.UserInformationWithIdDTO;
import com.wazzup.eventservice.event.service.CookieService;
import com.wazzup.eventservice.ticket.dto.BuyTicketDTO;
import com.wazzup.eventservice.ticket.repository.TicketRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@Service
@Slf4j
public class TicketService {
    private final TicketRepository ticketRepository;
    private final CookieService cookieService;
    private final UserServiceClient userServiceClient;

    public void buyTicket(BuyTicketDTO dto, HttpServletRequest request) {
        UserInformationWithIdDTO userInformation = getUserInformation(request);

    }

    private UserInformationWithIdDTO getUserInformation(HttpServletRequest request) {
        String token = CookieService.getTokenFromRequest(request);
        return userServiceClient.getUserByToken(token);
    }
}
