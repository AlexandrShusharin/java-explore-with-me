package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.model.EventAddDto;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.EventUpdateDto;
import ru.practicum.event.service.EventService;
import ru.practicum.request.model.RequestDto;
import ru.practicum.request.model.RequestStatusUpdateDto;
import ru.practicum.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@Validated
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final RequestService requestService;

    @PostMapping
    public EventFullDto addEvent(@RequestBody EventAddDto eventAddDto, @PathVariable long userId) {
        return eventService.addEvent(userId, eventAddDto);
    }

    @GetMapping
    public List<EventFullDto> getAllUserEvents(@PathVariable long userId,
                                               @RequestParam(name = "from", defaultValue = "0") int from,
                                               @RequestParam(name = "size", defaultValue = "1000000") int size) {
        return eventService.getUserEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable long userId, @PathVariable long eventId) {
        return eventService.getEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable long userId, @PathVariable long eventId,
                                    @RequestBody EventUpdateDto eventUpdateDto) {
        return eventService.updateEvent(userId, eventId, eventUpdateDto);
    }

    @GetMapping("/{eventId}/requests/")
    public List<RequestDto> getEventRequests(@PathVariable long userId, @PathVariable long eventId) {
        return requestService.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/")
    public List<RequestDto> setRequestsStatus(@PathVariable long userId, @PathVariable long eventId,
                                              @RequestBody RequestStatusUpdateDto requestStatusUpdateDto) {
        return requestService.updateRequestStatus(userId, eventId, requestStatusUpdateDto);
    }
}
