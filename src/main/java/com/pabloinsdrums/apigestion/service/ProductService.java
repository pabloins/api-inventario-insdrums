package com.pabloinsdrums.apigestion.service;

import com.pabloinsdrums.apigestion.dto.SaveProductDto;
import com.pabloinsdrums.apigestion.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);

    Optional<Product> findOneById(Long productId);

    Product createOne(SaveProductDto saveProductDto);

    Product updateOneById(Long productId, SaveProductDto saveProductDto);

    Product disabledOneById(Long productId);

    Product deleteOneById(Long productId);
}
