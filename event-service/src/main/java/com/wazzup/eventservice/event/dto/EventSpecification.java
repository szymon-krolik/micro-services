package com.wazzup.eventservice.event.dto;

import com.wazzup.eventservice.event.dto.EventSearchParamDTO;
import com.wazzup.eventservice.event.entity.Event;
import com.wazzup.eventservice.event.entity.EventAddress;
import com.wazzup.eventservice.event.entity.EventType;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@AllArgsConstructor
public class EventSpecification implements Specification<Event> {

    private final EventSearchParamDTO eventSearchParamDTO;

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        query.distinct(Boolean.TRUE);

        if (Objects.nonNull(eventSearchParamDTO.getName())) {
            Predicate likeNameCondition = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + eventSearchParamDTO.getName().toLowerCase(Locale.ROOT) + "%");
            predicateList.add(likeNameCondition);
        }

        if (Objects.nonNull(eventSearchParamDTO.getCity())) {
            Join<EventAddress, Event> eventAddressEventJoin = root.join("eventAddress");
            predicateList.add(criteriaBuilder.like(eventAddressEventJoin.get("city"), "%" + eventSearchParamDTO.getCity() + "%"));
        }

        if (Objects.nonNull(eventSearchParamDTO.getEventTypes()) && !eventSearchParamDTO.getEventTypes().isEmpty()) {
            Join<EventType, Event> eventTypeEventJoin = root.join("eventTypes");
            predicateList.add(eventTypeEventJoin.in(eventSearchParamDTO.getEventTypes()));
        }

        if (Objects.nonNull(eventSearchParamDTO.getOwner())) {
            predicateList.add(criteriaBuilder.equal(root.get("userId"), eventSearchParamDTO.getOwner()));
        }

        if (Objects.nonNull(eventSearchParamDTO.getStartDate())) {
            predicateList.add(criteriaBuilder.equal(root.get("startDate"), eventSearchParamDTO.getStartDate()));
        }

        if (Objects.nonNull(eventSearchParamDTO.getStartDate())) {
            predicateList.add(criteriaBuilder.equal(root.get("endDate"), eventSearchParamDTO.getEndDate()));
        }

        if (Objects.nonNull(eventSearchParamDTO.getName())) {
            predicateList.add(criteriaBuilder.equal(root.get("name"), eventSearchParamDTO.getName()));
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
    }
}
