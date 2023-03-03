package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.model.Category;
import ru.practicum.event.model.*;
import ru.practicum.event.service.EventService;

import javax.validation.Valid;
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
                                    @Valid @RequestBody EventAdminUpdateDto eventAdminUpdateDto) {
        return eventService.updateEventByAdmin(eventId, eventAdminUpdateDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getAllEvents(@RequestParam (required = false) List<Long> users,
                                            @RequestParam (required = false) List<EventState> states,
                                            @RequestParam (required = false) List<Long> categories,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                            @RequestParam (required = false) LocalDateTime rangeStart,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                            @RequestParam (required = false) LocalDateTime rangeEnd,
                                            @RequestParam(defaultValue = "0") int from,
                                            @RequestParam(defaultValue = "1000000") int size) {
        return eventService.getEventByAdminFilter(users, states, categories, rangeStart, rangeEnd, from, size);
    }
}
