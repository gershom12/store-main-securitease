package com.example.store.service;

import com.example.store.dto.CreateOrderRequest;
import com.example.store.dto.OrderDTO;
import com.example.store.entity.Customer;
import com.example.store.entity.Order;
import com.example.store.entity.Product;
import com.example.store.mapper.OrderMapper;
import com.example.store.repository.CustomerRepository;
import com.example.store.repository.OrderRepository;
import com.example.store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository repo;
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;
    private final OrderMapper mapper;

    @Transactional(readOnly = true)
    public OrderDTO getById(Long id) {

        Order order = repo.findByIdWithRelations(id).orElseThrow(() -> new RuntimeException("Order not found"));

        return mapper.orderToOrderDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getAll() {

        return repo.findAllWithRelations().stream().map(mapper::orderToOrderDTO).toList();
    }

    public OrderDTO createOrder(CreateOrderRequest req) {

        Customer customer = customerRepo
                .findById(req.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Order order = new Order();
        order.setDescription(req.getDescription());
        order.setCustomer(customer);

        if (req.getProductIds() != null && !req.getProductIds().isEmpty()) {

            Set<Product> products = new HashSet<>(productRepo.findAllById(req.getProductIds()));

            order.setProducts(products);
        }

        return mapper.orderToOrderDTO(repo.save(order));
    }
}
