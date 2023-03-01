package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.model.Category;
import ru.practicum.event.model.EventAdminUpdateDto;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.EventState;
import ru.practicum.event.model.EventUpdateDto;
import ru.practicum.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@Validated
@RequiredArgsConstructor
public class EventAdminController {
    private final EventService eventService;

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(@PathVariable long eventId,
                                    @RequestBody EventAdminUpdateDto eventAdminUpdateDto) {
        return eventService.updateEventByAdmin(eventId, eventAdminUpdateDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getAllEvents(@RequestParam List<Integer> users,
                                           @RequestParam List<EventState> states,
                                           @RequestParam List<Category> categories,
                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                           @RequestParam LocalDateTime rangeStart,
                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                           @RequestParam LocalDateTime rangeEnd,
                                           @RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "1000000") int size) {
        return null;
        //метод не написан
    }

}
