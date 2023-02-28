package ru.practicum.request.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestStatusUpdateDto {
    private List<Long> requestIds;
    private RequestStatus status;
}
