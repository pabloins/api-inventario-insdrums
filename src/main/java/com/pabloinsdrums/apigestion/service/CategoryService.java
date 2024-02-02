package com.pabloinsdrums.apigestion.service;

import com.pabloinsdrums.apigestion.dto.SaveCategoryDto;
import com.pabloinsdrums.apigestion.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {
    Page<Category> findAll(Pageable pageable);

    Optional<Category> findOneById(Long categoryId);

    Category createOne(SaveCategoryDto saveCategoryDto);

    Category updateOneById(Long categoryId, SaveCategoryDto saveCategoryDto);

    Category disabledOneById(Long categoryId);
}
