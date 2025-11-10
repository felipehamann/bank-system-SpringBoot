package com.felipehamann.bankapi.controller;

import com.felipehamann.bankapi.dto.CreateCustomerRequest;
import com.felipehamann.bankapi.model.Customer;
import com.felipehamann.bankapi.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public Customer create(@Valid @RequestBody CreateCustomerRequest request) {
        Customer customer = Customer.builder()
                .fullName(request.fullName())
                .email(request.email())
                .build();

        return customerRepository.save(customer);
    }

    @GetMapping
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }


    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Customer not found"
                ));
    }

}
