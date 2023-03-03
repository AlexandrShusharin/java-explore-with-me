package ru.practicum.event.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventUserUpdateDto extends EventUpdateDto {
    @NotNull
    private EventUserStateAction stateAction;
}
