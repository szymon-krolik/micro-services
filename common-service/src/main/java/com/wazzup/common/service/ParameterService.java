package com.wazzup.common.service;

import com.wazzup.common.dto.ParameterDTO;
import com.wazzup.common.entity.Parameter;
import com.wazzup.common.exception.ParameterNotFoundException;
import com.wazzup.common.repository.ParameterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ParameterService {
    private final ParameterRepository parameterRepository;
    //todo mapper parametrow na dto zrobic
    public ParameterDTO getParameterByCode(String code) {
        List<Parameter> all = parameterRepository.findAll();
        all.forEach(x -> System.out.println(x.getCode()));
        return ParameterDTO.of(parameterRepository.findByCode(code).orElseThrow(() -> new ParameterNotFoundException()));
    }
}
