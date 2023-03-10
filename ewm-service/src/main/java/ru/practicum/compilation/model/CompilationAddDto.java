package ru.practicum.compilation.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompilationAddDto {
    @NotBlank
    @NotNull
    private String title;
    private boolean pinned;
    List<Long> events;
}
