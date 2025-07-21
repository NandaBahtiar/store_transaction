package com.HCS_IDN.HCS_IDN.controllers;

import com.HCS_IDN.HCS_IDN.dtos.CustomerCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.CustomerDto;
import com.HCS_IDN.HCS_IDN.dtos.CustomerUpdateDto;
import com.HCS_IDN.HCS_IDN.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Page<CustomerDto> getAllCustomers(Pageable pageable) {
        return customerService.getAllCustomers(pageable);
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@Valid @RequestBody CustomerCreateDto customerCreateDto) {
        return customerService.createCustomer(customerCreateDto);
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerUpdateDto customerUpdateDto) {
        return customerService.updateCustomer(id, customerUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/search")
    public Page<CustomerDto> searchCustomers(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) LocalDate birthdate,
                                             @RequestParam(required = false) String birthplace,
                                             Pageable pageable) {
        return customerService.searchCustomers(name, birthdate, birthplace, pageable);
    }
}
