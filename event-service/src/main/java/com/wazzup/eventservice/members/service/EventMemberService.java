package com.wazzup.eventservice.members.service;

import com.wazzup.eventservice.event.dto.user.UserInformationWithIdDTO;
import com.wazzup.eventservice.event.service.EventService;
import com.wazzup.eventservice.members.EventMemberMapper;
import com.wazzup.eventservice.members.entity.EventMember;
import com.wazzup.eventservice.members.exception.UserAlreadyJoinException;
import com.wazzup.eventservice.members.repository.EventMemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
@Slf4j
public class EventMemberService {
    private final EventMemberRepository eventMemberRepository;
    private final EventService eventService;

    /**
     * Funckcja umozliwiajaca na dolaczenie do wydarzenia
     * @param id id wydarzenia
     * @param request HttpRequest
     */
    public void sendJoinRequest(Long id, HttpServletRequest request) {
        UserInformationWithIdDTO userDto = eventService.getUserInformation(request);
        eventService.getEventByIdNotHistory(id);
        if (getMemberByEventIdAndUserId(id, userDto.getId()) != null)
            throw new UserAlreadyJoinException();

        try {
            eventMemberRepository.save(EventMember.builder()
                            .eventId(id)
                            .userId(id)
                    .build());
        } catch (Exception ex) {
            log.info("Err. join event: {}", ex.getMessage());
        }
    }

    /**
     * Funckcja umozliwiajaca na opuszczenie do wydarzenia
     * @param id id wydarzenia
     * @param request HttpRequest
     */
    public void sendLeaveRequest(Long id, HttpServletRequest request) {
        UserInformationWithIdDTO userDto = eventService.getUserInformation(request);
        eventService.getEventByIdNotHistory(id);
        EventMember eventMember = getMemberByEventIdAndUserId(id, userDto.getId());
        if (eventMember == null)
            return;

        try {
            eventMemberRepository.deleteById(eventMember.getId());
        } catch (Exception ex) {
            log.info("Err. leave event: {}", ex.getMessage());
        }
    }

    public EventMember getMemberByEventIdAndUserId(Long eventId, Long userId) {
        return eventMemberRepository.findByEventIdAndUserId(eventId, userId).orElseThrow(() -> new NotFoundException("Not found"));
    }
}
