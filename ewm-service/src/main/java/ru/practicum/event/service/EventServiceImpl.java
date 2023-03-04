package ru.practicum.event.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.StatisticServiceClient;
import ru.practicum.StatisticViewDto;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.model.*;
import ru.practicum.event.model.QEvent;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exceptions.InvalidParametersException;
import ru.practicum.exceptions.ObjectNotFoundException;
import ru.practicum.location.model.LocationMapper;
import ru.practicum.location.repository.LocationRepository;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final StatisticServiceClient statisticClient;

    @Override
    public EventFullDto addEvent(long userId, EventAddDto eventAddDto) {
        Event event = EventMapper.fromEventAddDtoToEvent(eventAddDto);
        checkEventDate(event.getEventDate());
        event.setInitiator(userRepository.getReferenceById(userId));
        event.setCategory(categoryRepository.getReferenceById(eventAddDto.getCategory()));
        event.setLocation(locationRepository.save(LocationMapper.locationDtoToLocation(eventAddDto.getLocation())));
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING);
        return EventMapper.fromEventToEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventFullDto> getUserEvents(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(getPageNumber(from, size), size);
        return getEventsFullDtoWithViews(eventRepository.findAllByInitiator_Id(userId, pageable).stream()
                .map(EventMapper::fromEventToEventFullDto)
                .collect(Collectors.toList()));
    }

    @Override
    public EventFullDto getEvent(long userId, long eventId) {
        return getEventFullDtoWithViews(EventMapper.fromEventToEventFullDto(eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Event with id=%d was not found", eventId)))));

    }

    @Override
    public EventFullDto getPublicEvent(long eventId) {
        return getEventFullDtoWithViews(EventMapper.fromEventToEventFullDto(eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Event with id=%d was not found", eventId)))));
    }

    @Override
    public EventFullDto updateEventByUser(long userId, long eventId, EventUserUpdateDto eventUpdateDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Event with id=%d was not found", eventId)));
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new InvalidParametersException(
                    String.format("Event with id=%d published and can`t be edit.", event.getId()));
        }

        event = setAndCheckEventUpdate(eventUpdateDto, event);

        if (eventUpdateDto.getStateAction() != null) {
            switch (eventUpdateDto.getStateAction()) {
                case CANCEL_REVIEW:
                    event.setState(EventState.CANCELED);
                    break;
                case SEND_TO_REVIEW:
                    event.setState(EventState.PENDING);
                    break;
            }
        }
        return getEventFullDtoWithViews(EventMapper.fromEventToEventFullDto(eventRepository.save(event)));
    }

    @Override
    public EventFullDto updateEventByAdmin(long eventId, EventAdminUpdateDto eventUpdateDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Event with id=%d was not found", eventId)));

        event = setAndCheckEventUpdate(eventUpdateDto, event);

        if (eventUpdateDto.getStateAction() != null) {
            switch (eventUpdateDto.getStateAction()) {
                case PUBLISH_EVENT:
                    switch (event.getState()) {
                        case PUBLISHED:
                            throw new InvalidParametersException(
                                    String.format("Event with id=%d is already published.", event.getId()));
                        case CANCELED:
                            throw new InvalidParametersException(
                                    String.format("Event with id=%d was canceled", event.getId()));
                    }
                    event.setState(EventState.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                    break;
                case REJECT_EVENT:
                    if (event.getState().equals(EventState.PUBLISHED)) {
                        throw new InvalidParametersException(
                                String.format("Event with id=%d published and can`t be canceled.", event.getId()));
                    }
                    event.setState(EventState.CANCELED);
                    break;
            }
        }
        return getEventFullDtoWithViews(EventMapper.fromEventToEventFullDto(eventRepository.save(event)));
    }

    @Override
    public List<EventFullDto> getEventByUserFilter(String text, List<Long> categories, Boolean paid,
                                                   LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                   Boolean onlyAvailable, EventSortType sort, int from,
                                                   int size) {
        Pageable pageable = PageRequest.of(getPageNumber(from, size), size);
        QEvent qevent = QEvent.event;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (text != null) {
            booleanBuilder.and(qevent.annotation.containsIgnoreCase(text)
                    .or(qevent.description.containsIgnoreCase(text)));
        }
        if (categories != null && !categories.isEmpty()) {
            booleanBuilder.and(qevent.category.id.in(categories));
        }
        if (rangeStart != null) {
            booleanBuilder.and(qevent.eventDate.after(rangeStart));
        }
        if (rangeEnd != null) {
            booleanBuilder.and(qevent.eventDate.before(rangeEnd));
        }
        if (paid != null) {
            booleanBuilder.and(qevent.paid.eq(paid));
            booleanBuilder.and(qevent.participantLimit.gt(qevent.requests.size()));
        }

        List<EventFullDto> events = eventRepository.findAll(booleanBuilder, pageable).stream()
                .map(EventMapper::fromEventToEventFullDto)
                .collect(Collectors.toList());

        if (onlyAvailable) {
            events = events.stream()
                    .filter(o -> o.getParticipantLimit() > o.getConfirmedRequests())
                    .collect(Collectors.toList());
        }

        events = getEventsFullDtoWithViews(events);
        switch (sort) {
            case EVENT_DATE:
                events.sort(Comparator.comparing(EventFullDto::getEventDate));
                break;
            case VIEWS:
                events.sort(Comparator.comparingLong(EventFullDto::getViews));
                break;
        }
        return events;
    }

    @Override
    public List<EventFullDto> getEventByAdminFilter(List<Long> users, List<EventState> states,
                                                    List<Long> categories,
                                                    LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                    int from, int size) {
        Pageable pageable = PageRequest.of(getPageNumber(from, size), size);
        QEvent qevent = QEvent.event;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (users != null && !categories.isEmpty()) {
            booleanBuilder.and(qevent.initiator.id.in(users));
        }
        if (states != null && !states.isEmpty()) {
            booleanBuilder.and(qevent.state.in(states));
        }
        if (categories != null && !categories.isEmpty()) {
            booleanBuilder.and(qevent.category.id.in(categories));
        }
        if (rangeStart != null) {
            booleanBuilder.and(qevent.eventDate.after(rangeStart));
        }
        if (rangeEnd != null) {
            booleanBuilder.and(qevent.eventDate.before(rangeEnd));
        }

        List<EventFullDto> events = eventRepository.findAll(booleanBuilder, pageable).stream()
                .map(EventMapper::fromEventToEventFullDto)
                .collect(Collectors.toList());

        events = getEventsFullDtoWithViews(events);
        return events;
    }

    private Event setAndCheckEventUpdate(EventUpdateDto eventUpdateDto, Event event) {
        if (eventUpdateDto.getTitle() != null && !eventUpdateDto.getTitle().equals(event.getTitle())) {
            event.setTitle(eventUpdateDto.getTitle());
        }
        if (eventUpdateDto.getAnnotation() != null && !eventUpdateDto.getTitle().equals(event.getAnnotation())) {
            event.setAnnotation(eventUpdateDto.getAnnotation());
        }
        if (eventUpdateDto.getDescription() != null && !eventUpdateDto.getDescription().equals(event.getDescription())) {
            event.setDescription(eventUpdateDto.getDescription());
        }
        if (eventUpdateDto.getEventDate() != null && !eventUpdateDto.getEventDate().equals(event.getEventDate())) {
            checkEventDate(eventUpdateDto.getEventDate());
            event.setEventDate(eventUpdateDto.getEventDate());
        }
        if (eventUpdateDto.getParticipantLimit() != null &&
                !eventUpdateDto.getParticipantLimit().equals(event.getParticipantLimit())) {
            event.setParticipantLimit(eventUpdateDto.getParticipantLimit());
        }
        if (eventUpdateDto.getPaid() != null &&
                !eventUpdateDto.getPaid().equals(event.isPaid())) {
            event.setPaid(eventUpdateDto.getPaid());
        }
        if (eventUpdateDto.getRequestModeration() != null &&
                !eventUpdateDto.getRequestModeration().equals(event.isRequestModeration())) {
            event.setRequestModeration(eventUpdateDto.getRequestModeration());
        }
        if (eventUpdateDto.getCategory() != null &&
                !eventUpdateDto.getCategory().equals(event.getCategory().getId())) {
            event.setCategory(categoryRepository.getReferenceById(eventUpdateDto.getCategory()));
        }
        return event;
    }

    private int getPageNumber(int from, int size) {
        return from / size;
    }

    private void checkEventDate(LocalDateTime eventTime) {
        if (eventTime.isBefore(LocalDateTime.now())) {
            throw new InvalidParametersException(
                    "Event date can`t be in past.");
        }
    }

    @Override
    public List<EventFullDto> getEventsFullDtoWithViews(List<EventFullDto> events) {
        if (events == null || events.isEmpty()) {
            return new ArrayList<>();
        }
        LocalDateTime minDate = events.stream()
                .sorted(Comparator.comparing(EventFullDto::getCreatedOn))
                .findFirst().get().getCreatedOn();

        List<String> uris = events.stream().map(o ->"/events/" + o.getId()).collect(Collectors.toList());

        HashMap<Long, Long> viewsMap = getEventsViews(uris, minDate);

        for (EventFullDto event : events) {
            if (viewsMap.containsKey(event.getId())) {
                event.setViews(viewsMap.get(event.getId()));
            }
        }
        return events;
    }

    private EventFullDto getEventFullDtoWithViews(EventFullDto event) {
        List<EventFullDto> events = getEventsFullDtoWithViews(List.of(event));
        return events.get(0);

    }

    private HashMap<Long, Long> getEventsViews(List<String> uris, LocalDateTime startDate) {
        ResponseEntity<Object> responseEntity = statisticClient.getStatistic(startDate, LocalDateTime.now(), uris);
        ObjectMapper mapper = new ObjectMapper();
        List<StatisticViewDto> views = mapper.convertValue(responseEntity.getBody(),
                new TypeReference<List<StatisticViewDto>>() { });

        HashMap<Long, Long> viewsMap = new HashMap<>();

        for (StatisticViewDto statView : views) {
            long hits = statView.getHits();
            long eventId = Long.parseLong(statView.getUri().split("/")[2]);
            viewsMap.put(eventId, hits);
        }
        return viewsMap;
    }

}
