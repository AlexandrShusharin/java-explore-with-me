package ru.practicum.compilation.service;

import ru.practicum.compilation.model.CompilationAddDto;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.model.CompilationUpdateDto;

import java.util.List;

public interface CompilationService {
    CompilationDto addCompilation(CompilationAddDto compilationAddDto);

    CompilationDto updateCompilation(long compilationId,
                                     CompilationUpdateDto compilationUpdateDto);

    void deleteCompilation(long compilationId);

    CompilationDto getCompilation(long compId);

    List<CompilationDto> getAllCompilation(boolean pinned, int from, int size);
}
