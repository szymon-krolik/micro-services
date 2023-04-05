package com.wazzup.eventservice.event.mapper;


import com.wazzup.eventservice.event.dto.EventAddressDTO;
import com.wazzup.eventservice.event.entity.EventAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventAddressMapper {

    EventAddress mapToEntity(EventAddressDTO eventAddressDTO);
}
