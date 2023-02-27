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
public class EventAdminUpdateDto{
    private long id;
    @NotBlank
    private String title;
    @NotBlank
    private String annotation;
    @NotBlank
    private long category;
    @NotBlank
    private String description;
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotNull
    private boolean paid;
    @PositiveOrZero
    private int participantLimit;
    @NotNull
    private boolean requestModeration;
    @NotNull
    private EventAdminStateAction stateAction;
}

