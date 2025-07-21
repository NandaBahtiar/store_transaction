package com.HCS_IDN.HCS_IDN.services;

import com.HCS_IDN.HCS_IDN.dtos.CustomerCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.CustomerDto;
import com.HCS_IDN.HCS_IDN.dtos.CustomerUpdateDto;
import com.HCS_IDN.HCS_IDN.models.Customer;
import com.HCS_IDN.HCS_IDN.models.User;
import com.HCS_IDN.HCS_IDN.repositories.CustomerRepository;
import com.HCS_IDN.HCS_IDN.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    public Page<CustomerDto> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable).map(this::toDto);
    }

    public CustomerDto getCustomerById(Long id) {
        return toDto(customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found")));
    }

    public CustomerDto createCustomer(CustomerCreateDto customerCreateDto) {
        User createdBy = userRepository.findById(customerCreateDto.getCreatedBy()).orElseThrow(() -> new RuntimeException("User not found"));
        Customer customer = new Customer();
        customer.setName(customerCreateDto.getName());
        customer.setBirthdate(customerCreateDto.getBirthdate());
        customer.setBirthplace(customerCreateDto.getBirthplace());
        customer.setCreatedBy(createdBy);
        return toDto(customerRepository.save(customer));
    }

    public CustomerDto updateCustomer(Long id, CustomerUpdateDto customerUpdateDto) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        User updatedBy = userRepository.findById(customerUpdateDto.getUpdatedBy()).orElseThrow(() -> new RuntimeException("User not found"));

        if (customerUpdateDto.getName() != null) {
            customer.setName(customerUpdateDto.getName());
        }
        if (customerUpdateDto.getBirthdate() != null) {
            customer.setBirthdate(customerUpdateDto.getBirthdate());
        }
        if (customerUpdateDto.getBirthplace() != null) {
            customer.setBirthplace(customerUpdateDto.getBirthplace());
        }
        customer.setUpdatedBy(updatedBy);
        return toDto(customerRepository.save(customer));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Page<CustomerDto> searchCustomers(String name, LocalDate birthdate, String birthplace, Pageable pageable) {
        // This will be implemented properly in a later step with custom repository methods
        return customerRepository.findAll(pageable).map(this::toDto);
    }

    private CustomerDto toDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setBirthdate(customer.getBirthdate());
        dto.setBirthplace(customer.getBirthplace());
        if (customer.getCreatedBy() != null) {
            dto.setCreatedBy(customer.getCreatedBy().getId());
        }
        if (customer.getUpdatedBy() != null) {
            dto.setUpdatedBy(customer.getUpdatedBy().getId());
        }
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setUpdatedAt(customer.getUpdatedAt());
        return dto;
    }
}
