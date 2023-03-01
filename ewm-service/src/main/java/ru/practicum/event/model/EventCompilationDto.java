package ru.practicum.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.category.model.Category;
import ru.practicum.location.model.LocationDto;
import ru.practicum.user.model.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventCompilationDto {
    private long id;
    private String title;
    private String annotation;
    private Category category;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private boolean paid;
    private UserShortDto initiator;
    private long confirmedRequests;
    private int views;
}
