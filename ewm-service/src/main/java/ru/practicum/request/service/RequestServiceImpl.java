package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.model.*;
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
    public List<RequestDto> updateRequestStatus(long userId, long requestId,
                                                RequestStatusUpdateDto updateRequests) {
        requestRepository.updateRequestsStatus(updateRequests.getRequestIds(), updateRequests.getStatus());
        return requestRepository.findAllByIds(updateRequests.getRequestIds()).stream()
                .map(RequestMapper::fromRequestToRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> getEventRequests(long userId, long eventId) {
        return requestRepository.findAllByEvent_Id(eventId).stream()
                .map(RequestMapper::fromRequestToRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> getUserRequests(long userId) {
        return requestRepository.findAllByRequester_Id(userId).stream()
                .map(RequestMapper::fromRequestToRequestDto)
                .collect(Collectors.toList());
    }

}
