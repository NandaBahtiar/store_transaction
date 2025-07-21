package com.HCS_IDN.HCS_IDN;

import com.HCS_IDN.HCS_IDN.dtos.CustomerCreateDto;
import com.HCS_IDN.HCS_IDN.dtos.CustomerDto;
import com.HCS_IDN.HCS_IDN.dtos.CustomerUpdateDto;
import com.HCS_IDN.HCS_IDN.models.Customer;
import com.HCS_IDN.HCS_IDN.models.User;
import com.HCS_IDN.HCS_IDN.repositories.CustomerRepository;
import com.HCS_IDN.HCS_IDN.repositories.UserRepository;
import com.HCS_IDN.HCS_IDN.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCustomers() {
        User user = new User(1L, "testuser", "test@example.com", "password123", User.Role.ADMIN, LocalDateTime.now(), LocalDateTime.now());
        Customer customer1 = new Customer(1L, "John Doe", LocalDate.of(1990, 5, 15), "New York", user, user, LocalDateTime.now(), LocalDateTime.now());
        Customer customer2 = new Customer(2L, "Jane Smith", LocalDate.of(1988, 11, 22), "Los Angeles", user, user, LocalDateTime.now(), LocalDateTime.now());
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> customerPage = new PageImpl<>(Arrays.asList(customer1, customer2), pageable, 2);

        when(customerRepository.findAll(pageable)).thenReturn(customerPage);

        Page<CustomerDto> result = customerService.getAllCustomers(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("John Doe", result.getContent().get(0).getName());
    }

    @Test
    void getCustomerById() {
        User user = new User(1L, "testuser", "test@example.com", "password123", User.Role.ADMIN, LocalDateTime.now(), LocalDateTime.now());
        Customer customer = new Customer(1L, "John Doe", LocalDate.of(1990, 5, 15), "New York", user, user, LocalDateTime.now(), LocalDateTime.now());
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerDto foundCustomer = customerService.getCustomerById(1L);

        assertNotNull(foundCustomer);
        assertEquals("John Doe", foundCustomer.getName());
    }

    @Test
    void getCustomerByIdNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> customerService.getCustomerById(1L));
    }

    @Test
    void createCustomer() {
        User user = new User(1L, "testuser", "test@example.com", "password123", User.Role.ADMIN, LocalDateTime.now(), LocalDateTime.now());
        CustomerCreateDto createDto = new CustomerCreateDto();
        createDto.setName("New Customer");
        createDto.setBirthdate(LocalDate.of(2000, 1, 1));
        createDto.setBirthplace("Test City");
        createDto.setCreatedBy(1L);

        Customer savedCustomer = new Customer(1L, "New Customer", LocalDate.of(2000, 1, 1), "Test City", user, null, LocalDateTime.now(), LocalDateTime.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDto createdCustomer = customerService.createCustomer(createDto);

        assertNotNull(createdCustomer);
        assertEquals("New Customer", createdCustomer.getName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void updateCustomer() {
        User user = new User(1L, "testuser", "test@example.com", "password123", User.Role.ADMIN, LocalDateTime.now(), LocalDateTime.now());
        Customer existingCustomer = new Customer(1L, "Old Name", LocalDate.of(1990, 1, 1), "Old Place", user, user, LocalDateTime.now(), LocalDateTime.now());
        CustomerUpdateDto updateDto = new CustomerUpdateDto();
        updateDto.setName("Updated Name");
        updateDto.setUpdatedBy(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(existingCustomer);

        CustomerDto updatedCustomer = customerService.updateCustomer(1L, updateDto);

        assertNotNull(updatedCustomer);
        assertEquals("Updated Name", updatedCustomer.getName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void deleteCustomer() {
        doNothing().when(customerRepository).deleteById(1L);

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    void searchCustomers() {
        User user = new User(1L, "testuser", "test@example.com", "password123", User.Role.ADMIN, LocalDateTime.now(), LocalDateTime.now());
        Customer customer1 = new Customer(1L, "John Doe", LocalDate.of(1990, 5, 15), "New York", user, user, LocalDateTime.now(), LocalDateTime.now());
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> customerPage = new PageImpl<>(Arrays.asList(customer1), pageable, 1);

        when(customerRepository.findAll(pageable)).thenReturn(customerPage);

        Page<CustomerDto> result = customerService.searchCustomers("John", null, null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("John Doe", result.getContent().get(0).getName());
    }
}
