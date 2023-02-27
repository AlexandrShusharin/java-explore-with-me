package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.model.EventAddDto;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.EventUpdateDto;
import ru.practicum.event.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@Validated
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

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
}
