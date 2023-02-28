package ru.practicum.compilation.model;

import lombok.*;
import ru.practicum.event.model.EventCompilationDto;

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
    List<EventCompilationDto> events;
}
