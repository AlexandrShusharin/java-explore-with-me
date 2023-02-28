package ru.practicum.compilation.service;

import ru.practicum.compilation.model.CompilationAddDto;
import ru.practicum.compilation.model.CompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationDto addCompilation(CompilationAddDto compilationAddDto);

    CompilationDto updateCompilation(long compilationId,
                                     CompilationAddDto compilationAddDto);

    void deleteCompilation(long compilationId);

    CompilationDto getCompilation(long compId);

    List<CompilationDto> getAllCompilation(boolean pinned, int from, int size);
}
