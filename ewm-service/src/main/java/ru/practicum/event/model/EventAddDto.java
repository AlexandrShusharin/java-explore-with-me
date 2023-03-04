package ru.practicum.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.location.model.LocationDto;

import javax.validation.constraints.*;
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
    @Positive
    private long category;
    @NotBlank
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotNull
    private boolean paid;
    @PositiveOrZero
    private int participantLimit;
    @NotNull
    private boolean requestModeration;
    @NotNull
    private LocationDto location;

}
