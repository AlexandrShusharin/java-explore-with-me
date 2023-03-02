package ru.practicum.event.model;

import ru.practicum.category.model.CategoryMapper;
import ru.practicum.location.model.LocationMapper;
import ru.practicum.request.model.RequestStatus;
import ru.practicum.user.model.UserMapper;

public class EventMapper {
    //Мапперы для ДТО
    public static Event fromEventAddDtoToEvent(EventAddDto eventAddDto) {
        return Event.builder()
                .title(eventAddDto.getTitle())
                .annotation(eventAddDto.getAnnotation())
                .description(eventAddDto.getDescription())
                .eventDate(eventAddDto.getEventDate())
                .paid(eventAddDto.isPaid())
                .participantLimit(eventAddDto.getParticipantLimit())
                .requestModeration(eventAddDto.isRequestModeration())
                .build();
    }

    public static EventFullDto fromEventToEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .category(CategoryMapper.fromCategoryToCategoryEventDto(event.getCategory()))
                .eventDate(event.getEventDate())
                .paid(event.isPaid())
                .requestModeration(event.isRequestModeration())
                .participantLimit(event.getParticipantLimit())
                .state(event.getState())
                .createdOn(event.getCreatedOn())
                .location(LocationMapper.locationToLocationDto(event.getLocation()))
                .initiator(UserMapper.fromUserToUserShortDto(event.getInitiator()))
                .publishedOn(event.getPublishedOn())
                .confirmedRequests(event.getRequests().stream()
                        .filter(o -> o.getStatus().equals(RequestStatus.CONFIRMED)).count())
                .views(100)
                .build();
    }

    public static EventShortDto fromEventFullDtoEventShortDto(EventFullDto eventFullDto) {
        return EventShortDto.builder()
                .id(eventFullDto.getId())
                .title(eventFullDto.getTitle())
                .annotation(eventFullDto.getAnnotation())
                .description(eventFullDto.getDescription())
                .category(eventFullDto.getCategory())
                .eventDate(eventFullDto.getEventDate())
                .paid(eventFullDto.isPaid())
                .initiator(eventFullDto.getInitiator())
                .confirmedRequests(eventFullDto.getConfirmedRequests())
                .views(100)
                .build();
    }

    public static EventShortDto fromEventToEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .category(CategoryMapper.fromCategoryToCategoryEventDto(event.getCategory()))
                .eventDate(event.getEventDate())
                .paid(event.isPaid())
                .initiator(UserMapper.fromUserToUserShortDto(event.getInitiator()))
                .confirmedRequests(event.getRequests().stream()
                        .filter(o -> o.getStatus().equals(RequestStatus.CONFIRMED)).count())
                .views(100)
                .build();
    }
}
