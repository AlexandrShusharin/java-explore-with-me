package ru.practicum.compilation.model;

import lombok.*;
import ru.practicum.event.model.EventShortDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompilationDto {
    private long id;
    private String title;
    private boolean pinned;
    List<EventShortDto> events;
}
