package com.wazzup.eventservice.event.mapper;

import com.wazzup.eventservice.event.dto.CreateEventDTO;
import com.wazzup.eventservice.event.dto.EventInformationDTO;
import com.wazzup.eventservice.event.dto.UpdateEventDTO;
import com.wazzup.eventservice.event.entity.Event;
import com.wazzup.eventservice.event.entity.EventType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(ignore = true, target = "eventTypes")
    Event toEntity(CreateEventDTO dto);
    @Mapping(source = "eventTypes",target = "eventTypes",qualifiedByName = "mapEventTypes")
    @Mapping(source = "eventAddress.city", target = "eventAddress.city")
    EventInformationDTO toEventInformationDto(Event event);

    @Mapping(source = "eventInformation.eventAddress", target = "eventAddress")
    @Mapping(source = "eventInformation.name", target = "name")
    @Mapping(source = "eventInformation.description", target = "description")
    @Mapping(source = "eventInformation.startDate", target = "startDate")
    @Mapping(source = "eventInformation.endDate", target = "endDate")
    @Mapping(source = "eventInformation.maxMembers", target = "maxMembers")
    @Mapping(source = "eventInformation.qrCode", target = "qrCode")
    Event toEntity(UpdateEventDTO updateEventDTO);

    @Named("mapEventTypes")
    default List<String> mapEventTypes(List<EventType> eventTypes){
        return eventTypes.stream()
                .map(EventType::getName)
                .collect(Collectors.toList());
    }
}
