package ru.practicum.category.service;

import ru.practicum.category.model.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(Long catId, CategoryDto categoryDto);

    void deleteCategory(Long catId);

    CategoryDto getCategory(Long catId);

    List<CategoryDto> getCategoriesList(int from, int size);
}
