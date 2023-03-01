package ru.practicum.category.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEventDto {
    private long id;
    private String name;
}
