package com.HCS_IDN.HCS_IDN.controllers;

import com.HCS_IDN.HCS_IDN.dtos.TaxCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.TaxDto;
import com.HCS_IDN.HCS_IDN.dtos.TaxUpdateDto;
import com.HCS_IDN.HCS_IDN.services.TaxService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taxes")
public class TaxController {
    private final TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping
    public List<TaxDto> getAllTaxes() {
        return taxService.getAllTaxes();
    }

    @GetMapping("/{id}")
    public TaxDto getTaxById(@PathVariable Long id) {
        return taxService.getTaxById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaxDto createTax(@Valid @RequestBody TaxCreateDto taxCreateDto) {
        return taxService.createTax(taxCreateDto);
    }

    @PutMapping("/{id}")
    public TaxDto updateTax(@PathVariable Long id, @Valid @RequestBody TaxUpdateDto taxUpdateDto) {
        return taxService.updateTax(id, taxUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTax(@PathVariable Long id) {
        taxService.deleteTax(id);
    }
}
