package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.RequestDto;
import ru.practicum.request.model.RequestMapper;
import ru.practicum.request.model.RequestStatus;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public RequestDto addRequest(long userId, long eventId) {
        Request request = Request.builder()
                .created(LocalDateTime.now())
                .event(eventRepository.getReferenceById(eventId))
                .requester(userRepository.getReferenceById(userId))
                .status(RequestStatus.PENDING)
                .build();
        return RequestMapper.fromRequestToRequestDto(requestRepository.save(request));
    }

    @Override
    public RequestDto cancelRequest(long userId, long requestId) {
        Request request = requestRepository.getReferenceById(requestId);
        request.setStatus(RequestStatus.PENDING);
        return RequestMapper.fromRequestToRequestDto(requestRepository.save(request));
    }

    @Override
    public List<RequestDto> getUserRequests(long userId) {
        return requestRepository.findAllByRequester_Id(userId).stream()
                .map(RequestMapper::fromRequestToRequestDto)
                .collect(Collectors.toList());
    }

}
