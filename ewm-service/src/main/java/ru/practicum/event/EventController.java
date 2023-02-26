package ru.practicum.event;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.model.EventAddDto;
import ru.practicum.event.model.EventFullDto;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@Validated
public class EventController {
    @PostMapping
    public EventFullDto addEvent(@RequestBody EventAddDto eventAddDto, @PathVariable String userId) {
        return null;
    }

    @GetMapping
    public List<EventFullDto> getAllUserEvents(@PathVariable String userId) {
        return null;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable String userId, @PathVariable String eventId) {
        return null;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable String userId, @PathVariable String eventId, @RequestBody ) {
        return null;
    }
}
