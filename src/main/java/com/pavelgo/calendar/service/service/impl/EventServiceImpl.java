package com.pavelgo.calendar.service.service.impl;

import com.pavelgo.calendar.service.dto.EventDto;
import com.pavelgo.calendar.service.entity.EventEntity;
import com.pavelgo.calendar.service.exception.EventNotFoundException;
import com.pavelgo.calendar.service.repository.EventRepository;
import com.pavelgo.calendar.service.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pavelgo.calendar.service.mapper.EventMapper.EVENT_MAPPER;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public List<EventDto> findAll() {
        return EVENT_MAPPER.toDtos(eventRepository.findAll());
    }

    @Override
    public EventDto findOneOrThrow(Long id) {
        return eventRepository.findById(id)
                .map(EVENT_MAPPER::toDto)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    @Override
    public EventDto create(EventDto dto) {
        EventEntity savedEvent = eventRepository.save(EVENT_MAPPER.toEntity(dto));
        return EVENT_MAPPER.toDto(savedEvent);
    }

    @Override
    public EventDto update(Long id, EventDto dto) {
        return eventRepository.findById(id)
                .map(entity -> {
                    entity.setTitle(dto.title());
                    entity.setDescription(dto.description());
                    entity.setStartDateTime(dto.startDateTime());
                    entity.setEndDateTime(dto.endDateTime());
                    entity.setLocation(dto.location());
                    return EVENT_MAPPER.toDto(eventRepository.save(entity));
                })
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    @Override
    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
