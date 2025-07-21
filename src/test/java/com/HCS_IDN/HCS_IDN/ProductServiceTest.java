package com.HCS_IDN.HCS_IDN;

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
import com.HCS_IDN.HCS_IDN.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private TaxRepository taxRepository;

    @Mock
    private ProductTaxRepository productTaxRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts() {
        Product product1 = new Product(1L, "Laptop", BigDecimal.valueOf(1200.00), LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        Product product2 = new Product(2L, "Mouse", BigDecimal.valueOf(25.00), LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<ProductDto> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("Laptop", products.get(0).getName());
    }

    @Test
    void getProductById() {
        Product product = new Product(1L, "Laptop", BigDecimal.valueOf(1200.00), LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDto foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals("Laptop", foundProduct.getName());
    }

    @Test
    void getProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProductById(1L));
    }

    @Test
    void createProduct() {
        ProductCreateDto createDto = new ProductCreateDto();
        createDto.setName("New Product");
        createDto.setBasePrice(BigDecimal.valueOf(100.00));

        Product savedProduct = new Product(1L, "New Product", BigDecimal.valueOf(100.00), LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductDto createdProduct = productService.createProduct(createDto);

        assertNotNull(createdProduct);
        assertEquals("New Product", createdProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct() {
        Product existingProduct = new Product(1L, "Old Product", BigDecimal.valueOf(50.00), LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        ProductUpdateDto updateDto = new ProductUpdateDto();
        updateDto.setName("Updated Product");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        ProductDto updatedProduct = productService.updateProduct(1L, updateDto);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deleteProduct() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void addTaxToProduct() {
        Product product = new Product(1L, "Laptop", BigDecimal.valueOf(1200.00), LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        Tax tax = new Tax(1L, "VAT", BigDecimal.valueOf(20.00), new HashSet<>());

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(taxRepository.findById(1L)).thenReturn(Optional.of(tax));
        when(productTaxRepository.save(any(ProductTax.class))).thenReturn(new ProductTax());

        productService.addTaxToProduct(1L, 1L);

        verify(productTaxRepository, times(1)).save(any(ProductTax.class));
    }

    @Test
    void removeTaxFromProduct() {
        Product product = new Product(1L, "Laptop", BigDecimal.valueOf(1200.00), LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        Tax tax = new Tax(1L, "VAT", BigDecimal.valueOf(20.00), new HashSet<>());
        ProductTax productTax = new ProductTax(1L, product, tax);

        when(productTaxRepository.findAll()).thenReturn(Arrays.asList(productTax));
        doNothing().when(productTaxRepository).delete(any(ProductTax.class));

        productService.removeTaxFromProduct(1L, 1L);

        verify(productTaxRepository, times(1)).delete(any(ProductTax.class));
    }

    @Test
    void getTaxesForProduct() {
        Product product = new Product(1L, "Laptop", BigDecimal.valueOf(1200.00), LocalDateTime.now(), LocalDateTime.now(), new HashSet<>());
        Tax tax1 = new Tax(1L, "VAT", BigDecimal.valueOf(20.00), new HashSet<>());
        Tax tax2 = new Tax(2L, "Luxury", BigDecimal.valueOf(10.00), new HashSet<>());
        ProductTax productTax1 = new ProductTax(1L, product, tax1);
        ProductTax productTax2 = new ProductTax(2L, product, tax2);

        when(productTaxRepository.findAll()).thenReturn(Arrays.asList(productTax1, productTax2));

        List<TaxDto> taxes = productService.getTaxesForProduct(1L);

        assertNotNull(taxes);
        assertEquals(2, taxes.size());
        assertEquals("VAT", taxes.get(0).getName());
    }
}
