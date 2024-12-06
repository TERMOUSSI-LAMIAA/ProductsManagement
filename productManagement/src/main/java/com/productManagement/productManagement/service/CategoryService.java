package com.productManagement.productManagement.service;

import com.productManagement.productManagement.dto.requestDTO.CategoryRequestDTO;
import com.productManagement.productManagement.dto.responseDTO.CategoryResponseDTO;
import com.productManagement.productManagement.entities.Category;
import com.productManagement.productManagement.exceptions.CategoryException;
import com.productManagement.productManagement.mappers.CategoryMapper;
import com.productManagement.productManagement.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryMapper.toEntity(categoryRequestDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException(id));

        categoryMapper.updateEntityFromDTO(categoryRequestDTO, existingCategory);
        Category updatedCategory = categoryRepository.save(existingCategory);

        return categoryMapper.toDTO(updatedCategory);
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryException(id));
        return categoryMapper.toDTO(category);
    }

    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryException(id);
        }
        categoryRepository.deleteById(id);
    }

}
