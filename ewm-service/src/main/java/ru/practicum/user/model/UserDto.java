package ru.practicum.user.model;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private long id;
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    @Email
    private String email;
}
