package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exceptions.InvalidParametersException;
import ru.practicum.exceptions.ObjectNotFoundException;
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
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Event with id=%d was not found", eventId)));

        if (requestRepository.findFirstByRequester_IdAndEvent_Id(userId, eventId) != null) {
            throw new InvalidParametersException(
                    String.format("Request with user_id=%d and event_id=%d is already exist", userId, eventId));
        }

        if (userId == event.getInitiator().getId()) {
            throw new InvalidParametersException(
                    String.format("User with user_id=%d is owner of event with event_id=%d", userId, eventId));
        }

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new InvalidParametersException(
                    String.format("Event with event_id=%d is not published", eventId));
        }
        if (isEventRequestOverloaded(event)) {
            throw new InvalidParametersException(
                    String.format("Event with event_id=%d is already full.", eventId));
        }

        Request request = Request.builder()
                .created(LocalDateTime.now())
                .event(eventRepository.getReferenceById(eventId))
                .requester(userRepository.getReferenceById(userId))
                .build();
        if (event.isRequestModeration()) {
            request.setStatus(RequestStatus.PENDING);
        } else {
            request.setStatus(RequestStatus.CONFIRMED);
        }
        return RequestMapper.fromRequestToRequestDto(requestRepository.save(request));
    }

    @Override
    public RequestDto cancelRequest(long userId, long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Request with id=%d was not found", requestId)));
        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            throw new InvalidParametersException(
                    String.format("Request with id=%d confirmed and can`t be canceled.", requestId));
        }
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.fromRequestToRequestDto(requestRepository.save(request));
    }

    @Override
    public RequestsUpdatedDto updateRequestStatus(long userId, long eventId,
                                                  RequestStatusUpdateDto updateRequests) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Event with id=%d was not found", eventId)));

        List<Request> requests = requestRepository.findAllByIdIn(updateRequests.getRequestIds());

        if (isEventRequestOverloaded(event)) {
            throw new InvalidParametersException(
                    String.format("Request limit for event with event_id=%d overload.",
                            event.getId()));
        }

        for (Request request : requests) {
            if (updateRequests.getStatus().equals(RequestStatus.REJECTED) &&
                    request.getStatus().equals(RequestStatus.CONFIRMED)) {
                throw new InvalidParametersException(
                        String.format("Request with request_id=%d is already confirmed and can`t be rejected.",
                                request.getId()));

            }
            request.setStatus(updateRequests.getStatus());
            requestRepository.save(request);
        }

        return RequestsUpdatedDto.builder()
                .confirmedRequests(requestRepository.findAllByIdInAndStatus(updateRequests.getRequestIds(),
                                RequestStatus.CONFIRMED).stream()
                        .map(RequestMapper::fromRequestToRequestDto)
                        .collect(Collectors.toList()))
                .rejectedRequests(requestRepository.findAllByIdInAndStatus(updateRequests.getRequestIds(),
                                RequestStatus.REJECTED).stream()
                        .map(RequestMapper::fromRequestToRequestDto)
                        .collect(Collectors.toList()))
                .build();
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

    private boolean isEventRequestOverloaded(Event event) {
        return (event.getRequests().stream()
                .filter(o -> o.getStatus().equals(RequestStatus.CONFIRMED))
                .count() >= event.getParticipantLimit());
    }
}
