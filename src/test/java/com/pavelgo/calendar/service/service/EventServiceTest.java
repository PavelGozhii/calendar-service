package com.pavelgo.calendar.service.service;

import com.pavelgo.calendar.service.dto.EventDto;
import com.pavelgo.calendar.service.entity.EventEntity;
import com.pavelgo.calendar.service.exception.EventNotFoundException;
import com.pavelgo.calendar.service.repository.EventRepository;
import com.pavelgo.calendar.service.service.impl.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@MockitoSettings
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void shouldFindAllEvents() {
        //given
        EventEntity entity1 = getEventEntity(1L, "Meeting");
        EventEntity entity2 = getEventEntity(2L, "Conference");

        when(eventRepository.findAll()).thenReturn(List.of(entity1, entity2));

        //when
        List<EventDto> events = eventService.findAll();

        //then
        assertThat(events).isNotEmpty();
        assertThat(events.size()).isEqualTo(2);
        verify(eventRepository).findAll();
        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void shouldFindById() {
        //given
        Long eventId = 1L;
        EventEntity entity = getEventEntity(eventId, "Meeting");
        EventDto expectedResult = EventDto.builder()
                .id(eventId)
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startDateTime(entity.getStartDateTime())
                .endDateTime(entity.getEndDateTime())
                .build();

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(entity));

        //when
        EventDto result = eventService.findOneOrThrow(eventId);

        //then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedResult);
        verify(eventRepository).findById(eventId);
        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void shouldThrowExceptionIfEventNotFound() {
        //given
        Long eventId = 1L;

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> eventService.findOneOrThrow(eventId))
                .isInstanceOf(EventNotFoundException.class);
        verify(eventRepository).findById(eventId);
        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void shouldSave() {
        //given
        Long eventId = 1L;
        EventEntity entity = getEventEntity(null,  "Meeting");
        EventDto eventDto = EventDto.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startDateTime(entity.getStartDateTime())
                .endDateTime(entity.getEndDateTime())
                .build();
        when(eventRepository.save(entity)).thenReturn(getEventEntity(eventId,  "Meeting"));

        //when
        eventService.create(eventDto);

        //then
        verify(eventRepository).save(entity);
        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void shouldUpdate() {
        //given
        Long eventId = 1L;
        EventEntity entity = getEventEntity(eventId,  "Meeting");
        EventDto eventDto = EventDto.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startDateTime(entity.getStartDateTime())
                .endDateTime(entity.getEndDateTime())
                .build();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(entity));
        when(eventRepository.save(entity)).thenReturn(entity);

        //when
        eventService.update(eventId, eventDto);

        //then
        verify(eventRepository).findById(eventId);
        verify(eventRepository).save(entity);
        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void shouldThrowExceptionWhenUpdateAndEventNotFound() {
        //given
        Long eventId = 1L;
        EventEntity entity = getEventEntity(eventId,  "Meeting");
        EventDto eventDto = EventDto.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startDateTime(entity.getStartDateTime())
                .endDateTime(entity.getEndDateTime())
                .build();
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> eventService.update(eventId, eventDto))
                .isInstanceOf(EventNotFoundException.class);

        //then
        verify(eventRepository).findById(eventId);
        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void shouldDelete() {
        //given
        Long eventId = 1L;

        //when
        eventService.delete(eventId);

        //then
        verify(eventRepository).deleteById(eventId);
        verifyNoMoreInteractions(eventRepository);
    }

    private EventEntity getEventEntity(Long id, String title) {
        EventEntity entity = new EventEntity();
        entity.setId(id);
        entity.setTitle(title);
        entity.setStartDateTime(LocalDateTime.now());
        entity.setEndDateTime(LocalDateTime.now().plusHours(1));
        return entity;
    }
}