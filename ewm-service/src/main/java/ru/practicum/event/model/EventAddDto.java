package ru.practicum.event.model;

import lombok.*;
import ru.practicum.category.model.Category;

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
public class EventAddDto {
    private long id;
    @NotBlank
    private String title;
    @NotBlank
    private String annotation;
    @NotBlank
    private Category category;
    @NotBlank
    private String description;
    @Future
    private LocalDateTime eventDate;
    @NotNull
    private boolean paid;
    @PositiveOrZero
    private int participantLimit;
    @NotNull
    private boolean requestModeration;
}
