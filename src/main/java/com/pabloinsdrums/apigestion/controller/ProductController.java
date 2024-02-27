package com.pabloinsdrums.apigestion.controller;

import com.pabloinsdrums.apigestion.dto.SaveProductDto;
import com.pabloinsdrums.apigestion.model.entity.Product;
import com.pabloinsdrums.apigestion.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> findAll(Pageable pageable) {
        Page<Product> productPage = productService.findAll(pageable);

        if(productPage.hasContent()) {
            return ResponseEntity.ok(productPage);
        }

        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('READ_ONE_PRODUCT')")
    @GetMapping("/{productId}")
    public ResponseEntity<Product> findOneById(@PathVariable Long productId) {
        Optional<Product> product = productService.findOneById(productId);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PreAuthorize("hasAuthority('CREATE_ONE_PRODUCT')")
    @PostMapping
    public ResponseEntity<Product> createOne(@RequestBody @Valid SaveProductDto saveProductDto) {
        Product product = productService.createOne(saveProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PreAuthorize("hasAuthority('UPDATE_ONE_PRODUCT')")
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateOneById(@PathVariable Long productId,
                                                 @RequestBody @Valid SaveProductDto saveProductDto) {
        Product product = productService.updateOneById(productId, saveProductDto);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PreAuthorize("hasAuthority('DISABLE_ONE_PRODUCT')")
    @PutMapping("/{productId}/disabled")
    public ResponseEntity<Product> disableOneById(@PathVariable Long productId) {
        Product product = productService.disabledOneById(productId);
        return ResponseEntity.ok(product);
    }

    @PreAuthorize("hasAuthority('DELETE_ONE_PRODUCT')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Product> deleteOneById(@PathVariable Long productId) {
        Product product = productService.deleteOneById(productId);
        return ResponseEntity.ok(product);
    }
}
