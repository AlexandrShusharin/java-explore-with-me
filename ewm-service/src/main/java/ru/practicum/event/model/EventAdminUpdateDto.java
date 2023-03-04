package ru.practicum.event.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventAdminUpdateDto extends EventUpdateDto {
    private EventAdminStateAction stateAction;
}

