package ru.practicum.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventUpdateDto {
    protected long id;
    protected String title;
    protected String annotation;
    @Positive
    protected Long category;
    protected String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime eventDate;
    protected Boolean paid;
    @PositiveOrZero
    protected Integer participantLimit;
    protected Boolean requestModeration;
}
