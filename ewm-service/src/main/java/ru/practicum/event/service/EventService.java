package ru.practicum.event.service;

import ru.practicum.event.model.*;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventFullDto addEvent(long userId, EventAddDto eventAddDto);

    List<EventFullDto> getUserEvents(long userId, int from, int size);

    EventFullDto getEvent(long userId, long eventId);

    EventFullDto getPublicEvent(long eventId);

    EventFullDto updateEventByUser(long userId, long eventId, EventUserUpdateDto eventUpdateDto);

    EventFullDto updateEventByAdmin (long eventId, EventAdminUpdateDto eventAdminUpdateDto);

    List<EventFullDto> getEventByUserFilter(String text, List<Long> categories, Boolean paid,
                                             LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                             Boolean onlyAvailable, EventSortType sort, int from,
                                             int size);

    List<EventFullDto> getEventByAdminFilter(List<Long> users, List<EventState> states,
                                              List<Long> categories,
                                              LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                              int from, int size);
}
