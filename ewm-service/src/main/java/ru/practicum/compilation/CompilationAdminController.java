package ru.practicum.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.model.CompilationAddDto;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.model.CompilationUpdateDto;
import ru.practicum.compilation.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class CompilationAdminController {
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilation(@Valid @RequestBody CompilationAddDto compilationAddDto) {
        return compilationService.addCompilation(compilationAddDto);
    }


    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@PathVariable Long compId,
                                      @Valid @RequestBody CompilationUpdateDto compilationUpdateDto) {
        return compilationService.updateCompilation(compId, compilationUpdateDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long compId) {
        compilationService.deleteCompilation(compId);
    }

}
