package com.wazzup.eventservice.members.controller;


import com.wazzup.eventservice.members.service.EventMemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Event member Managment")
@RestController
@RequestMapping("/api/member")
@AllArgsConstructor
public class EventMemberController {
    private final EventMemberService eventMemberService;

    @PostMapping("{id}")
    public void sendJoinRequest(@RequestParam Long id, HttpServletRequest request) {
        eventMemberService.sendJoinRequest(id, request);
    }
}
