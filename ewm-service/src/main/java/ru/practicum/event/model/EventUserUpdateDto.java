package ru.practicum.event.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventUserUpdateDto extends EventUpdateDto {
    private EventUserStateAction stateAction;
}
