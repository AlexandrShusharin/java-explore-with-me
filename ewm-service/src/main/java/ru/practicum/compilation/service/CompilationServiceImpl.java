package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.model.CompilationAddDto;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.model.CompilationUpdateDto;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventMapper;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Override
    public CompilationDto addCompilation(CompilationAddDto compilationAddDto) {
        Compilation compilation = Compilation.builder()
                .title(compilationAddDto.getTitle())
                .pinned(compilationAddDto.isPinned())
                .build();
        List<Event> events = new ArrayList<>();
        if (!compilationAddDto.getEvents().isEmpty()) {
            for (long eventId : compilationAddDto.getEvents()) {
                events.add(eventRepository.getReferenceById(eventId));
            }
        }
        compilation.setEvents(events);
        return compilationToCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public CompilationDto updateCompilation(long compId,
                                            CompilationUpdateDto compilationUpdateDto) {
        Compilation compilation = compilationRepository.getReferenceById(compId);
        compilation.setTitle(compilationUpdateDto.getTitle());
        compilation.setPinned(compilationUpdateDto.isPinned());
        if (!compilationUpdateDto.getEvents().isEmpty()) {
            List<Event> events = new ArrayList<>();
            for (long eventId : compilationUpdateDto.getEvents()) {
                events.add(eventRepository.getReferenceById(eventId));
            }
            compilation.setEvents(events);
        }
        return compilationToCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto getCompilation(long compId) {
        return compilationToCompilationDto(compilationRepository.getReferenceById(compId));
    }

    @Override
    public List<CompilationDto> getAllCompilation(boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(getPageNumber(from, size), size);
        return compilationRepository.findAllByPinned(pinned, pageable).stream()
                .map(this::compilationToCompilationDto)
                .collect(Collectors.toList());
    }


    private CompilationDto compilationToCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.isPinned())
                .events(compilation.getEvents().stream()
                        .map(EventMapper::fromEventToEventShortDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private int getPageNumber(int from, int size) {
        return from / size;
    }
}
