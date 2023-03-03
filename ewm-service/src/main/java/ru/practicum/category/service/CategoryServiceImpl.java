package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        return CategoryMapper.fromCategoryToCategoryDto(
                categoryRepository.save(CategoryMapper.fromCategoryDtoToCategory(categoryDto)));
    }

    @Override
    public CategoryDto updateCategory(Long catId, CategoryDto categoryDto) {
        categoryDto.setId(catId);
        return CategoryMapper.fromCategoryToCategoryDto(
                categoryRepository.save(CategoryMapper.fromCategoryDtoToCategory(categoryDto)));
    }

    @Override
    public void deleteCategory(Long catId) {
        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        return  CategoryMapper.fromCategoryToCategoryDto(
                categoryRepository.findById(catId).orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Category with id=%d was not found", catId))));
    }

    @Override
    public List<CategoryDto> getCategoriesList(int from, int size) {
        Pageable pageable = PageRequest.of(getPageNumber(from, size), size);
        return categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::fromCategoryToCategoryDto)
                .collect(Collectors.toList());
    }
    private int getPageNumber(int from, int size) {
        return from / size;
    }
}
