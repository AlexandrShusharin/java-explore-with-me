package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.model.EventAdminUpdateDto;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.EventUpdateDto;
import ru.practicum.event.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/admin/events")
@Validated
@RequiredArgsConstructor
public class EventAdminController {
    private final EventService eventService;

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable long eventId,
                                    @RequestBody EventAdminUpdateDto eventAdminUpdateDto) {
        return eventService.updateEventByAdmin(eventId, eventAdminUpdateDto);
    }

    @GetMapping
    public List<EventFullDto> getAllEvents(@PathVariable long userId,
                                               @RequestParam(name = "from", defaultValue = "0") int from,
                                               @RequestParam(name = "size", defaultValue = "1000000") int size) {
        return null;
        //метод не написан
    }

}
