package com.pavelgo.calendar.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavelgo.calendar.service.dto.EventDto;
import com.pavelgo.calendar.service.exception.EventNotFoundException;
import com.pavelgo.calendar.service.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EventService eventService;

    @Test
    void shouldGetEvents() throws Exception {
        //given
        EventDto event1 = EventDto.builder()
                .id(1L)
                .title("Meeting")
                .build();
        EventDto event2 = EventDto.builder()
                .id(2L)
                .title("Lunch")
                .build();
        when(eventService.findAll()).thenReturn(List.of(event1, event2));

        // when & then
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Meeting"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Lunch"));
    }

    @Test
    void shouldGetEvent() throws Exception {
        //given
        EventDto event = EventDto.builder()
                .id(1L)
                .title("Conference")
                .build();

        when(eventService.findOneOrThrow(1L)).thenReturn(event);

        // when & then
        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Conference"));
    }

    @Test
    void shouldGetErrorWhenEventNotFound() throws Exception {
        //given
        when(eventService.findOneOrThrow(99L))
                .thenThrow(new EventNotFoundException(99L));

        //when & then
        mockMvc.perform(get("/events/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateEvent() throws Exception {
        //given
        EventDto event = EventDto.builder()
                .id(1L)
                .title("Conference")
                .description("Conference")
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusHours(1))
                .build();
        EventDto savedEvent = event.toBuilder()
                .id(1L)
                .build();

        when(eventService.create(event)).thenReturn(savedEvent);

        // when & then
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Conference"))
                .andExpect(jsonPath("$.description").value("Conference"));
    }

    @Test
    void createEvent_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // given
        EventDto event = EventDto.builder()
                .id(1L)
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusHours(1))
                .build();

        // when & then
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isBadRequest());
    }
}
