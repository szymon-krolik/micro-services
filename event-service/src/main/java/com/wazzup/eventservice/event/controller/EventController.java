package com.wazzup.eventservice.event.controller;

import com.wazzup.eventservice.event.dto.CreateEventDTO;
import com.wazzup.eventservice.event.dto.EventInformationDTO;
import com.wazzup.eventservice.event.dto.EventSearchParamDTO;
import com.wazzup.eventservice.event.dto.UpdateEventDTO;
import com.wazzup.eventservice.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Event Managment")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;

    @Operation(summary = "Create new event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success create new event"),
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("")
    public EventInformationDTO createEvent(@RequestBody CreateEventDTO dto, HttpServletRequest request) {
        return eventService.createEvent(dto, request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("")
    public EventInformationDTO updateEvent(@RequestBody UpdateEventDTO dto, HttpServletRequest request) {
        return eventService.updateEvent(dto, request);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id, HttpServletRequest request) {
        eventService.deleteEvent(id, request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public EventInformationDTO getEventById(@PathVariable Long id, HttpServletRequest request) {
        return eventService.getEventById(id, request);
    }

    @GetMapping("")
    public Page<EventInformationDTO> getAllEvents(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "dsc", required = false) String sortDir,
            @RequestBody EventSearchParamDTO eventSearchParam
    ) {
        return eventService.getAllEvents(pageNo, pageSize, sortBy, sortDir, eventSearchParam);
    }


}
