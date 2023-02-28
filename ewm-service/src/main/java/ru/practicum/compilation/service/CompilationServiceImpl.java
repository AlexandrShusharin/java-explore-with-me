package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.model.CompilationAddDto;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventCompilationDto;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.user.model.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto addCompilation(CompilationAddDto compilationAddDto) {
        Compilation compilation = Compilation.builder()
                .title(compilationAddDto.getTitle())
                .pinned(compilationAddDto.isPinned())
                .build();
        if (!compilationAddDto.getEvents().isEmpty()) {
            List<Event> events = new ArrayList<>();
            for (long eventId : compilationAddDto.getEvents()) {
                events.add(eventRepository.getReferenceById(eventId));
            }
            compilation.setEvents(events);
        }
        return compilationToCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public CompilationDto updateCompilation(long compId,
                                            CompilationAddDto compilationAddDto) {
        Compilation compilation = compilationRepository.getReferenceById(compId);
        compilation.setTitle(compilationAddDto.getTitle());
        compilation.setPinned(compilationAddDto.isPinned());
        if (!compilationAddDto.getEvents().isEmpty()) {
            List<Event> events = new ArrayList<>();
            for (long eventId : compilationAddDto.getEvents()) {
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
                        .map(this::eventToEventCompilationDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private EventCompilationDto eventToEventCompilationDto(Event event) {
        return EventCompilationDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .category(event.getCategory())
                .eventDate(event.getEventDate())
                .paid(event.isPaid())
                .initiator(UserMapper.fromUserToUserShortDto(event.getInitiator()))
                //.confirmedRequests()
                //.views()
                .build();
    }

    private int getPageNumber(int from, int size) {
        return from / size;
    }
}
