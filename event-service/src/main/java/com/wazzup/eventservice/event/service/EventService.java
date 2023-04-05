package com.wazzup.eventservice.event.service;

import com.wazzup.eventservice.event.dto.EventSpecification;
import com.wazzup.eventservice.event.client.CommonServiceClient;
import com.wazzup.eventservice.event.client.UserServiceClient;
import com.wazzup.eventservice.event.dto.CreateEventDTO;
import com.wazzup.eventservice.event.dto.EventInformationDTO;
import com.wazzup.eventservice.event.dto.EventSearchParamDTO;
import com.wazzup.eventservice.event.dto.UpdateEventDTO;
import com.wazzup.eventservice.event.dto.user.UserInformationWithIdDTO;
import com.wazzup.eventservice.event.entity.Event;
import com.wazzup.eventservice.event.entity.EventType;
import com.wazzup.eventservice.event.enums.ParameterCodeE;
import com.wazzup.eventservice.event.exception.*;
import com.wazzup.eventservice.event.mapper.EventMapper;

import com.wazzup.eventservice.event.mapper.EventAddressMapper;
import com.wazzup.eventservice.event.repository.EventRepository;
import com.wazzup.eventservice.event.repository.EventTypeRepository;
import com.wazzup.eventservice.qrcode.service.QrCodeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
    private final CommonServiceClient commonServiceClient;
    private final UserServiceClient userServiceClient;
    private final EventMapper eventMapper;
    private final EventTypeRepository eventTypeRepository;
    private final EventAddressMapper eventAddressMapper;
    private final QrCodeService qrCodeService;
    @Autowired
    private Validator validator;

//    @Value("${event.message.url}")
//    private String eventUrl;


    /**
     * Funkcja odpowiedzialna za tworzenie wydarzenia
     * @param createEventDTO Dane potrzebne do stworzenia wydarzenia
     * @param request HttpRequest do pobrania ciasteczka uzytkownika
     * @return podstawowe informacje o wydarzeniu
     */
    @Transactional
    public EventInformationDTO createEvent(CreateEventDTO createEventDTO, HttpServletRequest request) {

        Set<ConstraintViolation<CreateEventDTO>> validationError = validator.validate(createEventDTO);
        if (!validationError.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<CreateEventDTO> constraintViolation : validationError) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), validationError);
        }
        UserInformationWithIdDTO userDto = getUserInformation(request);
        canUserCreateEvent(userDto.getId(), userDto.getAuthorities());
        checkEventDate(createEventDTO.getStartDate(), createEventDTO.getEndDate());
        Event event = eventMapper.toEntity(createEventDTO);
        event.setEventTypes(getEventTypesById(createEventDTO.getEventTypes()));
        event.setUserId(userDto.getId());
        event.setEventAddress(eventAddressMapper.mapToEntity(createEventDTO.getEventAddress()));
        try {
            Event savedEvent = eventRepository.save(event);
            if (createEventDTO.isQrCode()) {
                savedEvent.setQrCodeContent(qrCodeService.createQrCode(savedEvent));
                eventRepository.save(savedEvent);
            }
            return eventMapper.toEventInformationDto(savedEvent);
        } catch (Exception ex) {
            log.info("ERROR_SAVE_EVENT: MSG: {}", ex.getMessage());
            throw new InternalApiException();
        }
    }

    /**
     * Funkcja sprawdzajaca czy uzytkownik może stworzyć kolejne wydarzenie
     * @param userId Id użytkownika
     * @param authorities Role użytkownika potrzebne do sprawdzenia czy jest premium
     */
    private void canUserCreateEvent(Long userId, List<String> authorities) {
        boolean isPremium = authorities.contains("USER_PREMIUM");
        int userEventsNumber = getNumberOfAllUserEvents(userId);
        if (isPremium && (userEventsNumber > getParameterValueByCode(ParameterCodeE.MAX_ILOSC_WYD_PREMIUM.getDescription())))
            throw new TooManyEventsException();

        if (!isPremium && (userEventsNumber > getParameterValueByCode(ParameterCodeE.MAX_ILOSC_WYD.getDescription())))
            throw new TooManyEventsException();
    }

    /**
     * Funkcja zliczajaca wszystkie wydarzenia uzytkownika
     * @param userId Id uzytkownika
     * @return Liczba wydarzen stworzonych przez uzytkownika
     */
    private int getNumberOfAllUserEvents(Long userId) {
        return eventRepository.findAllByUserId(userId).size();
    }

    /**
     * Pobiera parametr z bazy na podstawie kodu
     * @param parameterCode kod parametru
     * @return wartosc parametru
     */
    private int getParameterValueByCode(String parameterCode) {
        return Integer.parseInt(commonServiceClient.getParameterByCode(parameterCode).getValue());
    }

    /**
     * Funkcja sprawdzajaca poprawnosc dat
     * @return Czy daty są poprawne
     */
    private void checkEventDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isBefore(LocalDateTime.now()) || startDate.isAfter(endDate))
            throw new IncorrectDateException();
    }

    private List<EventType> getEventTypesById(Set<Long> eventTypesById) {
        return eventTypeRepository.findAllById(eventTypesById);
    }

    public EventInformationDTO updateEvent(UpdateEventDTO dto, HttpServletRequest request) {
        UserInformationWithIdDTO userDto = getUserInformation(request);
        checkEventOwner(userDto.getId(), dto.getEventId());
        checkEventDate(dto.getEventInformation().getStartDate(), dto.getEventInformation().getEndDate());
        List<EventType> eventTypes = getEventTypesById(dto.getEventInformation().getEventTypes());
        Event event = getEventById(dto.getEventId());

        try {
            Event savedEvent = eventRepository.save(updateEvent(event, dto, eventTypes));
            return eventMapper.toEventInformationDto(savedEvent);
        } catch (Exception ex) {
            log.error("ERR_UPDATE_EVENT: {}", ex.getMessage());
            throw new InternalApiException();
        }
    }

    public void deleteEvent(Long eventId, HttpServletRequest request) {
        UserInformationWithIdDTO userDto = getUserInformation(request);
        checkEventOwner(userDto.getId(), eventId);

        try {
            eventRepository.deleteById(eventId);
        } catch (Exception ex) {
            log.error("ERR_DELETE_EVENT: {}", ex.getMessage());
            throw new InternalApiException();
        }
    }
    public void checkEventOwner(Long userId, Long eventId) {
        Event event = getEventById(eventId);
        if (!Objects.equals(userId, event.getUserId()))
            throw new InvalidEventOwnerException();
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);
    }

    public EventInformationDTO getEventById(Long eventId, HttpServletRequest request) {
        return eventMapper.toEventInformationDto(eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new));
    }

    private Event updateEvent(Event event, UpdateEventDTO dto, List<EventType> eventTypes) {
        event.setEventTypes(eventTypes);
        event.setEventAddress(eventAddressMapper.mapToEntity(dto.getEventInformation().getEventAddress()));
        event.setDescription(dto.getEventInformation().getDescription());
        event.setName(dto.getEventInformation().getName());
        event.setStartDate(dto.getEventInformation().getStartDate());
        event.setEndDate(dto.getEventInformation().getEndDate());
        event.setMaxMembers(dto.getEventInformation().getMaxMembers());

        return event;
    }

    public Page<EventInformationDTO> getAllEvents(int pageNo, int pageSize, String sortBy, String sortDir, EventSearchParamDTO eventSearchParam) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, StringUtils.isEmpty(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC, StringUtils.isEmpty(sortBy) ? "id" : sortBy);
        return eventRepository.findAll(new EventSpecification(eventSearchParam), pageable).map(eventMapper::toEventInformationDto);
    }

    public UserInformationWithIdDTO getUserInformation(HttpServletRequest request) {
        String token = CookieService.getTokenFromRequest(request);
        return userServiceClient.getUserByToken(token);
    }

    public Event getEventByIdNotHistory(Long eventId) {
        return eventRepository.findByIdNotHistory(eventId).orElseThrow(EventNotFoundException::new);
    }

}
