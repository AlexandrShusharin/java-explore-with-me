package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.category.service.CategoryService;
import ru.practicum.event.model.*;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.location.model.LocationMapper;
import ru.practicum.location.repository.LocationRepository;
import ru.practicum.user.model.UserMapper;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    public EventFullDto addEvent(long userId, EventAddDto eventAddDto) {
        Event event = eventAddDtoToEvent(eventAddDto);
        event.setInitiator(UserMapper.fromUserDtoToUser(userService.getUser(userId)));
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING);
        return eventToEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventFullDto> getUserEvents(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(getPageNumber(from, size), size);
        return eventRepository.findAllByInitiator_Id(userId, pageable).stream()
                .map(this::eventToEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEvent(long userId, long eventId) {
        return eventToEventFullDto(eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Ошибка")));
    }

    @Override
    public EventFullDto updateEvent(long userId, long eventId, EventUpdateDto eventUpdateDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Ошибка"));
        switch (eventUpdateDto.getStateAction()) {
            case CANCEL_REVIEW:
                event.setState(EventState.CANCELED);
                break;
            case SEND_TO_REVIEW:
                event.setState(EventState.PENDING);
                break;
        }
        eventRepository.save(event);
        return eventToEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto updateEventByAdmin (long eventId, EventAdminUpdateDto eventAdminUpdateDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Ошибка"));
        switch (eventAdminUpdateDto.getStateAction()) {
            case PUBLISH_EVENT:
                event.setState(EventState.PUBLISHED);
                break;
            case REJECT_EVENT:
                event.setState(EventState.CANCELED);
                break;
        }
        eventRepository.save(event);
        return eventToEventFullDto(eventRepository.save(event));
    }

    //Мапперы для ДТО
    private Event eventAddDtoToEvent(EventAddDto eventAddDto) {
        return Event.builder()
                .title(eventAddDto.getTitle())
                .annotation(eventAddDto.getAnnotation())
                .description(eventAddDto.getDescription())
                .category(CategoryMapper.fromCategoryDtoToCategory(
                        categoryService.getCategory(eventAddDto.getCategory())))
                .location(locationRepository.save(LocationMapper.locationDtoToLocation(eventAddDto.getLocation())))
                .eventDate(eventAddDto.getEventDate())
                .paid(eventAddDto.isPaid())
                .participantLimit(eventAddDto.getParticipantLimit())
                .requestModeration(eventAddDto.isRequestModeration())
                .build();
    }

    private EventFullDto eventToEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .category(event.getCategory())
                .eventDate(event.getEventDate())
                .paid(event.isPaid())
                .requestModeration(event.isRequestModeration())
                .participantLimit(event.getParticipantLimit())
                .state(event.getState())
                .createdOn(event.getCreatedOn())
                .location(LocationMapper.locationToLocationDto(event.getLocation()))
                .initiator(UserMapper.fromUserToUserShortDto(event.getInitiator()))
                .build();
        //.publishedOn()
        //.confirmedRequests()
        //.views()
    }

    private int getPageNumber(int from, int size) {
        return from / size;
    }

}
