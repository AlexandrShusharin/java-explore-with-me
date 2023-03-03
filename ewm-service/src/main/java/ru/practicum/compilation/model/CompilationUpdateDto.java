package ru.practicum.compilation.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CompilationUpdateDto {
    private String title;
    private boolean pinned;
    List<Long> events;
}
