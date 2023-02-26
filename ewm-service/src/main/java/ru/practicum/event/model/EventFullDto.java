package ru.practicum.event.model;

import ru.practicum.category.model.Category;
import ru.practicum.location.model.LocationDto;
import ru.practicum.user.model.UserShotDto;

import java.time.LocalDateTime;

public class EventFullDto {
    private long id;
    private String title;
    private String annotation;
    private Category category;
    private String description;
    private LocalDateTime eventDate;
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;
    private LocationDto location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private UserShotDto initiator;
    private EventState state;
    private int confirmedRequests;
    private int views;
}
