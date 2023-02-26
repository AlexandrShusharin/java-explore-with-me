package ru.practicum.user.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserShotDto {
    private long id;
    private String name;
}
