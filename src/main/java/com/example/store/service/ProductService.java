package com.example.store.service;

import com.example.store.dto.CreateProductRequest;
import com.example.store.dto.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.mapper.ProductMapper;
import com.example.store.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository repo;
    private final ProductMapper mapper;

    public ProductDTO create(CreateProductRequest req) {

        Product product = new Product();
        product.setDescription(req.getDescription());

        return mapper.productToProductDTO(
                repo.save(product)
        );
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAll() {

        return mapper.productsToProductDTOs(
                repo.findAllWithOrders()
        );
    }

    @Transactional(readOnly = true)
    public ProductDTO getById(Long id) {

        Product product = repo.findByIdWithOrders(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        return mapper.productToProductDTO(product);
    }
}