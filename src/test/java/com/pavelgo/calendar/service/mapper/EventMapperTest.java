package com.pavelgo.calendar.service.mapper;

import com.pavelgo.calendar.service.dto.EventDto;
import com.pavelgo.calendar.service.entity.EventEntity;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.yaml.snakeyaml.events.Event;

import java.time.LocalDateTime;
import java.util.List;

import static com.pavelgo.calendar.service.mapper.EventMapper.EVENT_MAPPER;
import static org.assertj.core.api.Assertions.assertThat;

class EventMapperTest {

    @Test
    void shouldMapEntityToDto() {
        //given
        EventEntity entity = getEventEntity(1L, "Meeting", "Meeting description", "Kyiv");

        //when
        EventDto dto = EVENT_MAPPER.toDto(entity);

        //then
        assertThat(dto.id()).isEqualTo(entity.getId());
        assertThat(dto.title()).isEqualTo(entity.getTitle());
        assertThat(dto.description()).isEqualTo(entity.getDescription());
        assertThat(dto.startDateTime()).isEqualTo(entity.getStartDateTime());
        assertThat(dto.endDateTime()).isEqualTo(entity.getEndDateTime());
        assertThat(dto.location()).isEqualTo(entity.getLocation());
    }

    @Test
    void shouldMapDtoToEntity() {
        //given
        EventDto eventDto = EventDto.builder()
                .id(1L)
                .title("Daily standup")
                .description("Daily standup description")
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusHours(1))
                .location("Kyiv")
                .build();

        //when
        EventEntity entity = EVENT_MAPPER.toEntity(eventDto);

        //then
        assertThat(entity.getId()).isEqualTo(eventDto.id());
        assertThat(entity.getTitle()).isEqualTo(eventDto.title());
        assertThat(entity.getDescription()).isEqualTo(eventDto.description());
        assertThat(entity.getStartDateTime()).isEqualTo(eventDto.startDateTime());
        assertThat(entity.getEndDateTime()).isEqualTo(eventDto.endDateTime());
        assertThat(entity.getLocation()).isEqualTo(eventDto.location());
    }

    @Test
    void shouldMapDtosToEntities() {
        //given
        EventEntity entity1 = getEventEntity(1L, "Meeting", "Meeting description", "Kyiv");
        EventEntity entity2 = getEventEntity(2L, "Conference", "Conference description", "Lviv");

        //when
        List<EventDto> eventDtos = EVENT_MAPPER.toDtos(List.of(entity1, entity2));

        assertThat(entity1.getId()).isEqualTo(eventDtos.getFirst().id());
        assertThat(entity1.getTitle()).isEqualTo(eventDtos.getFirst().title());
        assertThat(entity1.getDescription()).isEqualTo(eventDtos.getFirst().description());
        assertThat(entity1.getStartDateTime()).isEqualTo(eventDtos.getFirst().startDateTime());
        assertThat(entity1.getEndDateTime()).isEqualTo(eventDtos.getFirst().endDateTime());
        assertThat(entity1.getLocation()).isEqualTo(eventDtos.getFirst().location());

        assertThat(entity2.getId()).isEqualTo(eventDtos.get(1).id());
        assertThat(entity2.getTitle()).isEqualTo(eventDtos.get(1).title());
        assertThat(entity2.getDescription()).isEqualTo(eventDtos.get(1).description());
        assertThat(entity2.getStartDateTime()).isEqualTo(eventDtos.get(1).startDateTime());
        assertThat(entity2.getEndDateTime()).isEqualTo(eventDtos.get(1).endDateTime());
        assertThat(entity2.getLocation()).isEqualTo(eventDtos.get(1).location());
    }

    private EventEntity getEventEntity(Long id, String title, String description, String location) {
        EventEntity entity = new EventEntity();
        entity.setId(id);
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setStartDateTime(LocalDateTime.now());
        entity.setEndDateTime(LocalDateTime.now().plusHours(1));
        entity.setLocation(location);
        return entity;
    }
}
