package ru.practicum.comment.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentAddDto {
    @NotBlank
    private String text;
}
