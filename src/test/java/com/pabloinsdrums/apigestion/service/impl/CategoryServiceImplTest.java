package com.pabloinsdrums.apigestion.service.impl;

import com.pabloinsdrums.apigestion.dto.SaveCategoryDto;
import com.pabloinsdrums.apigestion.model.entity.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findOneById() {
    }

    @Test
    void createOne() {
        //Arrange
        SaveCategoryDto saveCategoryDto = new SaveCategoryDto();
        saveCategoryDto.setName("revistas");

        //Act
        Category categoryTest = categoryService.createOne(saveCategoryDto);

        //Assert
        Assertions.assertThat(categoryTest).isNotNull();
        Assertions.assertThat(categoryTest.getId()).isGreaterThan(0);
    }

    @Test
    void createOneFailedNull() {
        //Arrange
        SaveCategoryDto saveCategoryDto = new SaveCategoryDto();
        saveCategoryDto.setName(null);

        //Act
        Category categoryTest = categoryService.createOne(saveCategoryDto);

        //Assert
        Assertions.assertThat(categoryTest.getName()).isNull();
        //Assertions.assertThat(categoryTest.getId()).isGreaterThan(0);
    }

    @Test
    void updateOneById() {
    }

    @Test
    void disabledOneById() {
    }
}