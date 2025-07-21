package com.HCS_IDN.HCS_IDN.controllers;

import com.HCS_IDN.HCS_IDN.dtos.ProductCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.ProductDto;
import com.HCS_IDN.HCS_IDN.dtos.ProductUpdateDto;
import com.HCS_IDN.HCS_IDN.dtos.TaxDto;
import com.HCS_IDN.HCS_IDN.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@Valid @RequestBody ProductCreateDto productCreateDto) {
        return productService.createProduct(productCreateDto);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateDto productUpdateDto) {
        return productService.updateProduct(id, productUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PostMapping("/{id}/taxes/{taxId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTaxToProduct(@PathVariable Long id, @PathVariable Long taxId) {
        productService.addTaxToProduct(id, taxId);
    }

    @DeleteMapping("/{id}/taxes/{taxId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTaxFromProduct(@PathVariable Long id, @PathVariable Long taxId) {
        productService.removeTaxFromProduct(id, taxId);
    }

    @GetMapping("/{id}/taxes")
    public List<TaxDto> getTaxesForProduct(@PathVariable Long id) {
        return productService.getTaxesForProduct(id);
    }
}
