package com.HCS_IDN.HCS_IDN.services;

import com.HCS_IDN.HCS_IDN.dtos.TaxCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.TaxDto;
import com.HCS_IDN.HCS_IDN.dtos.TaxUpdateDto;
import com.HCS_IDN.HCS_IDN.models.Tax;
import com.HCS_IDN.HCS_IDN.repositories.TaxRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxService {
    private final TaxRepository taxRepository;

    public TaxService(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    public List<TaxDto> getAllTaxes() {
        return taxRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public TaxDto getTaxById(Long id) {
        return toDto(taxRepository.findById(id).orElseThrow(() -> new RuntimeException("Tax not found")));
    }

    public TaxDto createTax(TaxCreateDto taxCreateDto) {
        Tax tax = new Tax();
        tax.setName(taxCreateDto.getName());
        tax.setPercentage(taxCreateDto.getPercentage());
        return toDto(taxRepository.save(tax));
    }

    public TaxDto updateTax(Long id, TaxUpdateDto taxUpdateDto) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new RuntimeException("Tax not found"));
        if (taxUpdateDto.getName() != null) {
            tax.setName(taxUpdateDto.getName());
        }
        if (taxUpdateDto.getPercentage() != null) {
            tax.setPercentage(taxUpdateDto.getPercentage());
        }
        return toDto(taxRepository.save(tax));
    }

    public void deleteTax(Long id) {
        taxRepository.deleteById(id);
    }

    private TaxDto toDto(Tax tax) {
        TaxDto dto = new TaxDto();
        dto.setId(tax.getId());
        dto.setName(tax.getName());
        dto.setPercentage(tax.getPercentage());
        return dto;
    }
}
