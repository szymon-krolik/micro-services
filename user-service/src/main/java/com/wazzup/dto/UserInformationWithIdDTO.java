package com.wazzup.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserInformationWithIdDTO{
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private String refreshToken;
    private List<String> authorities;
}
