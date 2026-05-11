package com.example.store.repository;

import com.example.store.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("""
        SELECT DISTINCT c
        FROM Customer c
        LEFT JOIN FETCH c.orders
        WHERE :query IS NULL
        OR LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    List<Customer> search(@Param("query") String query);
}
