package ru.practicum.request.model;

public final class RequestMapper {
    private RequestMapper() {
    }

    public static RequestDto fromRequestToRequestDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .requester(request.getRequester().getId())
                .event(request.getEvent().getId())
                .status(request.getStatus())
                .build();
    }
}
