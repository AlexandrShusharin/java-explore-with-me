package ru.practicum.category.model;

public final class CategoryMapper {
    private CategoryMapper() {
    }

    public static CategoryDto fromCategoryToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static CategoryEventDto fromCategoryToCategoryEventDto(Category category) {
        return CategoryEventDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category fromCategoryDtoToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }
}
