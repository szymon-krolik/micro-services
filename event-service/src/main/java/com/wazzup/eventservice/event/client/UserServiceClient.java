package com.wazzup.eventservice.event.client;

import com.wazzup.eventservice.event.dto.user.UserInformationWithIdDTO;
import com.wazzup.eventservice.event.enums.UserUrlE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
@Service
@Slf4j
public class UserServiceClient {
    private final WebClient USER_WEB_CLIENT;

    public UserServiceClient(@Value("${user.url}") String userUrl) {
        this.USER_WEB_CLIENT = WebClient.builder()
                .baseUrl(userUrl)
                .build();
    }

    //todo ogarnac wyjatki jakie rzuca yslyga
    public UserInformationWithIdDTO getUserByToken(String token) {
        return USER_WEB_CLIENT.get()
                .uri(UserUrlE.GET_USER_BY_TOKEN.getUrl())
                .accept(MediaType.APPLICATION_JSON)
                .cookie("TOKEN", token)
                .retrieve()
                .bodyToMono(UserInformationWithIdDTO.class)
                .block();
    }
}
