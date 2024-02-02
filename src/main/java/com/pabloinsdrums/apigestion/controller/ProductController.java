package com.pabloinsdrums.apigestion.controller;

import com.pabloinsdrums.apigestion.dto.SaveProductDto;
import com.pabloinsdrums.apigestion.model.entity.Product;
import com.pabloinsdrums.apigestion.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productservice;

    @GetMapping
    public ResponseEntity<Page<Product>> findAll(Pageable pageable) {
        Page<Product> productPage = productservice.findAll(pageable);

        if(productPage.hasContent()) {
            return ResponseEntity.ok(productPage);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findOneById(@PathVariable Long productId) {
        Optional<Product> product = productservice.findOneById(productId);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Product> createOne(@RequestBody @Valid SaveProductDto saveProductDto) {
        Product product = productservice.createOne(saveProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateOneById(@PathVariable Long productId,
                                                 @RequestBody @Valid SaveProductDto saveProductDto) {
        Product product = productservice.updateOneById(productId, saveProductDto);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/{productId}/disabled")
    public ResponseEntity<Product> disableOneById(@PathVariable Long productId) {
        Product product = productservice.disabledOneById(productId);
        return ResponseEntity.ok(product);
    }
}
