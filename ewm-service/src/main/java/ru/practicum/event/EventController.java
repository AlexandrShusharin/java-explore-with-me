package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.StatisticServiceClient;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.EventSortType;
import ru.practicum.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@Validated
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final StatisticServiceClient statisticClient;
    private static final String APP_NAME = "ewm-main-service";

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEvent(@PathVariable long eventId, HttpServletRequest request) {
        statisticClient.postStatistic(APP_NAME, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());
        return eventService.getPublicEvent(eventId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getAllEvents(@RequestParam(required = false) String text,
                                           @RequestParam(required = false) List<Long> categories,
                                           @RequestParam(defaultValue = "false") Boolean paid,
                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                           @RequestParam(required = false) LocalDateTime rangeStart,
                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                           @RequestParam(required = false) LocalDateTime rangeEnd,
                                           @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                           @RequestParam(defaultValue = "VIEWS") EventSortType sort,
                                           @RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "1000000") int size,
                                           HttpServletRequest request) {
        statisticClient.postStatistic(APP_NAME, request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());
        return eventService.getEventByUserFilter(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size);
    }
}
