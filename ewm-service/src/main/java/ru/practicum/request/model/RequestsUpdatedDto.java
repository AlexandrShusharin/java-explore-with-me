package ru.practicum.request.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestsUpdatedDto {
    List<RequestDto> confirmedRequests;
    List<RequestDto> rejectedRequests;
}
