package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.model.EventAddDto;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.EventUserUpdateDto;
import ru.practicum.event.service.EventService;
import ru.practicum.request.model.RequestDto;
import ru.practicum.request.model.RequestStatusUpdateDto;
import ru.practicum.request.model.RequestsUpdatedDto;
import ru.practicum.request.service.RequestService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@Validated
@RequiredArgsConstructor
public class EventUserController {
    private final EventService eventService;
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@Valid @RequestBody EventAddDto eventAddDto, @PathVariable long userId) {
        return eventService.addEvent(userId, eventAddDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getAllUserEvents(@PathVariable long userId,
                                               @RequestParam(name = "from", defaultValue = "0") int from,
                                               @RequestParam(name = "size", defaultValue = "1000000") int size) {
        return eventService.getUserEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEvent(@PathVariable long userId, @PathVariable long eventId) {
        return eventService.getEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(@PathVariable long userId, @PathVariable long eventId,
                                    @Valid @RequestBody EventUserUpdateDto eventUpdateDto) {
        return eventService.updateEventByUser(userId, eventId, eventUpdateDto);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<RequestDto> getEventRequests(@PathVariable long userId, @PathVariable long eventId) {
        return requestService.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public RequestsUpdatedDto setRequestsStatus(@PathVariable long userId, @PathVariable long eventId,
                                                @RequestBody RequestStatusUpdateDto requestStatusUpdateDto) {
        return requestService.updateRequestStatus(userId, eventId, requestStatusUpdateDto);
    }
}
