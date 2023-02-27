package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.model.RequestDto;
import ru.practicum.request.service.RequestService;

@RestController
@RequestMapping("/users/{userId}/requests")
@Validated
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    public RequestDto addRequest(@PathVariable long userId, @RequestParam long eventId) {
        return requestService.addRequest(userId, eventId);
    }
}
