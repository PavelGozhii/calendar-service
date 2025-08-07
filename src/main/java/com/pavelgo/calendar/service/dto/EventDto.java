package com.pavelgo.calendar.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record EventDto(
        Long id,
        @NotBlank String title,
        String description,
        @NotNull LocalDateTime startDateTime,
        @NotNull LocalDateTime endDateTime,
        String location) {
}
