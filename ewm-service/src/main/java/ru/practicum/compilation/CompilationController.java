package ru.practicum.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.model.CompilationAddDto;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.service.CompilationService;

import java.util.List;

@RestController("/compilations")
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(defaultValue = "false") boolean pinned,
                                                @RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "1000000") int size) {
        return compilationService.getAllCompilation(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getAllCompilation(@PathVariable Long compId) {
        return compilationService.getCompilation(compId);
    }
}
