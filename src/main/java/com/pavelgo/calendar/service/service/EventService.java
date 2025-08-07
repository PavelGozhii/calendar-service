package com.pavelgo.calendar.service.service;

import com.pavelgo.calendar.service.dto.EventDto;

import java.util.List;

public interface EventService {

    List<EventDto> findAll();

    EventDto findOneOrThrow(Long id);

    EventDto create(EventDto dto);

    EventDto update(Long id, EventDto dto);

    void delete(Long id);
}
