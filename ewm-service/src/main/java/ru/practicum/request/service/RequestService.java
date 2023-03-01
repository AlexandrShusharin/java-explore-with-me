package ru.practicum.request.service;

import ru.practicum.request.model.RequestDto;
import ru.practicum.request.model.RequestStatusUpdateDto;
import ru.practicum.request.model.RequestsUpdatedDto;

import java.util.List;

public interface RequestService {
    RequestDto addRequest(long userId, long eventId);

    RequestDto cancelRequest(long userId, long requestId);


   RequestsUpdatedDto updateRequestStatus(long userId, long requestId,
                                          RequestStatusUpdateDto updateRequests);

    List<RequestDto> getEventRequests(long userId, long eventId);

    List<RequestDto> getUserRequests(long userId);
}
