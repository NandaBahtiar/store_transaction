package com.HCS_IDN.HCS_IDN.services;

import com.HCS_IDN.HCS_IDN.dtos.ProductCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.ProductDto;
import com.HCS_IDN.HCS_IDN.dtos.ProductUpdateDto;
import com.HCS_IDN.HCS_IDN.dtos.TaxDto;
import com.HCS_IDN.HCS_IDN.models.Product;
import com.HCS_IDN.HCS_IDN.models.ProductTax;
import com.HCS_IDN.HCS_IDN.models.Tax;
import com.HCS_IDN.HCS_IDN.repositories.ProductRepository;
import com.HCS_IDN.HCS_IDN.repositories.ProductTaxRepository;
import com.HCS_IDN.HCS_IDN.repositories.TaxRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final TaxRepository taxRepository;
    private final ProductTaxRepository productTaxRepository;

    public ProductService(ProductRepository productRepository, TaxRepository taxRepository, ProductTaxRepository productTaxRepository) {
        this.productRepository = productRepository;
        this.taxRepository = taxRepository;
        this.productTaxRepository = productTaxRepository;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        return toDto(productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found")));
    }

    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        Product product = new Product();
        product.setName(productCreateDto.getName());
        product.setBasePrice(productCreateDto.getBasePrice());
        return toDto(productRepository.save(product));
    }

    public ProductDto updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        if (productUpdateDto.getName() != null) {
            product.setName(productUpdateDto.getName());
        }
        if (productUpdateDto.getBasePrice() != null) {
            product.setBasePrice(productUpdateDto.getBasePrice());
        }
        return toDto(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void addTaxToProduct(Long productId, Long taxId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Tax tax = taxRepository.findById(taxId).orElseThrow(() -> new RuntimeException("Tax not found"));
        ProductTax productTax = new ProductTax();
        productTax.setProduct(product);
        productTax.setTax(tax);
        productTaxRepository.save(productTax);
    }

    public void removeTaxFromProduct(Long productId, Long taxId) {
        // This is a simplified implementation. A better approach would be to find the specific ProductTax entry and delete it.
        productTaxRepository.findAll().stream()
                .filter(pt -> pt.getProduct().getId().equals(productId) && pt.getTax().getId().equals(taxId))
                .findFirst()
                .ifPresent(productTaxRepository::delete);
    }

    public List<TaxDto> getTaxesForProduct(Long productId) {
        return productTaxRepository.findAll().stream()
                .filter(pt -> pt.getProduct().getId().equals(productId))
                .map(pt -> toDto(pt.getTax()))
                .collect(Collectors.toList());
    }

    private ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setBasePrice(product.getBasePrice());
        return dto;
    }

    private TaxDto toDto(Tax tax) {
        TaxDto dto = new TaxDto();
        dto.setId(tax.getId());
        dto.setName(tax.getName());
        dto.setPercentage(tax.getPercentage());
        return dto;
    }
}
