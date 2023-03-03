package ru.practicum.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventUpdateDto{
    protected long id;
    @NotBlank
    protected String title;
    @NotBlank
    protected String annotation;
    @NotBlank
    protected Long category;
    @NotBlank
    protected String description;
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime eventDate;
    @NotNull
    protected Boolean paid;
    @PositiveOrZero
    protected Integer participantLimit;
    @NotNull
    protected Boolean requestModeration;
}
