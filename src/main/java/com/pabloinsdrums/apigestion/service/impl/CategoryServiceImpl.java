package com.pabloinsdrums.apigestion.service.impl;

import com.pabloinsdrums.apigestion.dto.SaveCategoryDto;
import com.pabloinsdrums.apigestion.exception.ObjectNotFoundException;
import com.pabloinsdrums.apigestion.model.entity.Category;
import com.pabloinsdrums.apigestion.repository.CategoryRepository;
import com.pabloinsdrums.apigestion.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findOneById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createOne(SaveCategoryDto saveCategoryDto) {
        Category category = new Category();
        category.setName(saveCategoryDto.getName());
        category.setStatus(Category.CategoryStatus.ENABLED);

        return categoryRepository.save(category);
    }

    @Override
    public Category updateOneById(Long categoryId, SaveCategoryDto saveCategoryDto) {
        Category categoryFromDB = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found with id "+ categoryId));
        categoryFromDB.setName(saveCategoryDto.getName());

        return categoryRepository.save(categoryFromDB);
    }

    @Override
    public Category disabledOneById(Long categoryId) {
        Category categoryFromDB = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found with id "+ categoryId));
        categoryFromDB.setStatus(Category.CategoryStatus.DISABLED);

        return categoryRepository.save(categoryFromDB);
    }
}
