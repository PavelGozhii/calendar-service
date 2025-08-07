package com.pavelgo.calendar.service.exception;

import lombok.Getter;

@Getter
public class EventNotFoundException extends RuntimeException {

    private final String message;

    public EventNotFoundException(Long id) {
        this.message = String.format("Event with id %s not found", id);
    }
}
