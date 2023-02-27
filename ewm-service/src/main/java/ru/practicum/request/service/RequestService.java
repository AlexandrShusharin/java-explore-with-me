package ru.practicum.request.service;

import ru.practicum.request.model.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto addRequest(long userId, long eventId);

    RequestDto cancelRequest(long userId, long requestId);

    List<RequestDto> getUserRequests(long userId);
}
