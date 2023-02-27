package ru.practicum.event.service;

import ru.practicum.event.model.EventAddDto;
import ru.practicum.event.model.EventAdminUpdateDto;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.EventUpdateDto;

import java.util.List;

public interface EventService {
    EventFullDto addEvent(long userId, EventAddDto eventAddDto);

    List<EventFullDto> getUserEvents(long userId, int from, int size);

    EventFullDto getEvent(long userId, long eventId);

    EventFullDto updateEvent(long userId, long eventId, EventUpdateDto eventUpdateDto);

    EventFullDto updateEventByAdmin (long eventId, EventAdminUpdateDto eventAdminUpdateDto);
}
