package com.pavelgo.calendar.service.mapper;

import com.pavelgo.calendar.service.dto.EventDto;
import com.pavelgo.calendar.service.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EventMapper {
    EventMapper EVENT_MAPPER = Mappers.getMapper(EventMapper.class);

    EventDto toDto(EventEntity entity);

    EventEntity toEntity(EventDto dto);

    List<EventDto> toDtos(List<EventEntity> entities);

}
