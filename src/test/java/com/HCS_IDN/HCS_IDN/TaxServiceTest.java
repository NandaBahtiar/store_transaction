package com.HCS_IDN.HCS_IDN;

import com.HCS_IDN.HCS_IDN.dtos.TaxCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.TaxDto;
import com.HCS_IDN.HCS_IDN.dtos.TaxUpdateDto;
import com.HCS_IDN.HCS_IDN.models.Tax;
import com.HCS_IDN.HCS_IDN.repositories.TaxRepository;
import com.HCS_IDN.HCS_IDN.services.TaxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaxServiceTest {

    @Mock
    private TaxRepository taxRepository;

    @InjectMocks
    private TaxService taxService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTaxes() {
        Tax tax1 = new Tax(1L, "VAT", BigDecimal.valueOf(20.00), new HashSet<>());
        Tax tax2 = new Tax(2L, "Sales Tax", BigDecimal.valueOf(8.00), new HashSet<>());
        when(taxRepository.findAll()).thenReturn(Arrays.asList(tax1, tax2));

        List<TaxDto> taxes = taxService.getAllTaxes();

        assertNotNull(taxes);
        assertEquals(2, taxes.size());
        assertEquals("VAT", taxes.get(0).getName());
    }

    @Test
    void getTaxById() {
        Tax tax = new Tax(1L, "VAT", BigDecimal.valueOf(20.00), new HashSet<>());
        when(taxRepository.findById(1L)).thenReturn(Optional.of(tax));

        TaxDto foundTax = taxService.getTaxById(1L);

        assertNotNull(foundTax);
        assertEquals("VAT", foundTax.getName());
    }

    @Test
    void getTaxByIdNotFound() {
        when(taxRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taxService.getTaxById(1L));
    }

    @Test
    void createTax() {
        TaxCreateDto createDto = new TaxCreateDto();
        createDto.setName("New Tax");
        createDto.setPercentage(BigDecimal.valueOf(5.00));

        Tax savedTax = new Tax(1L, "New Tax", BigDecimal.valueOf(5.00), new HashSet<>());
        when(taxRepository.save(any(Tax.class))).thenReturn(savedTax);

        TaxDto createdTax = taxService.createTax(createDto);

        assertNotNull(createdTax);
        assertEquals("New Tax", createdTax.getName());
        verify(taxRepository, times(1)).save(any(Tax.class));
    }

    @Test
    void updateTax() {
        Tax existingTax = new Tax(1L, "Old Tax", BigDecimal.valueOf(10.00), new HashSet<>());
        TaxUpdateDto updateDto = new TaxUpdateDto();
        updateDto.setName("Updated Tax");

        when(taxRepository.findById(1L)).thenReturn(Optional.of(existingTax));
        when(taxRepository.save(any(Tax.class))).thenReturn(existingTax);

        TaxDto updatedTax = taxService.updateTax(1L, updateDto);

        assertNotNull(updatedTax);
        assertEquals("Updated Tax", updatedTax.getName());
        verify(taxRepository, times(1)).save(any(Tax.class));
    }

    @Test
    void deleteTax() {
        doNothing().when(taxRepository).deleteById(1L);

        taxService.deleteTax(1L);

        verify(taxRepository, times(1)).deleteById(1L);
    }
}