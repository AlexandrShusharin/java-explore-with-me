package ru.practicum.event.model;

import ru.practicum.category.model.CategoryMapper;
import ru.practicum.location.model.LocationMapper;
import ru.practicum.request.model.RequestStatus;
import ru.practicum.user.model.UserMapper;

public class EventMapper {
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
                .confirmedRequests(getConfirmedRequestsCount(event))
                .views(0)
                .build();
    }

    public static EventShortDto fromEventFullDtoToEventShortDto(EventFullDto event) {
        return EventShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .category(event.getCategory())
                .eventDate(event.getEventDate())
                .paid(event.isPaid())
                .initiator(event.getInitiator())
                .confirmedRequests(event.getConfirmedRequests())
                .views(event.getViews())
                .build();
    }

    private static long getConfirmedRequestsCount(Event event) {
        if (event.getRequests() != null && event.getRequests().size() > 0) {
            return event.getRequests().stream()
                    .filter(o -> o.getStatus().equals(RequestStatus.CONFIRMED)).count();
        } else {
            return 0;
        }
    }
}
