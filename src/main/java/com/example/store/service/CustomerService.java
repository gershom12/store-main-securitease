package com.example.store.service;

import com.example.store.dto.CreateCustomerRequest;
import com.example.store.dto.CustomerDTO;
import com.example.store.entity.Customer;
import com.example.store.mapper.CustomerMapper;
import com.example.store.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository repo;
    private final CustomerMapper mapper;

    @Transactional(readOnly = true)
    public List<CustomerDTO> getCustomers(String query) {

        return mapper.customersToCustomerDTOs(
                repo.search(query)
        );
    }

    public CustomerDTO create(CreateCustomerRequest req) {

        Customer customer = new Customer();
        customer.setName(req.getName());

        return mapper.customerToCustomerDTO(
                repo.save(customer)
        );
    }
}