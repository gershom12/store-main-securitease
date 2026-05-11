package com.example.store.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.Set;

@Data
public class CreateOrderRequest {
    @NotBlank
    private String description;

    @NotNull private Long customerId;

    private Set<Long> productIds;
}
