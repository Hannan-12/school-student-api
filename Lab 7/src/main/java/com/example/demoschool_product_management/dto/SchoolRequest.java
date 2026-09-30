package com.example.demoschool_product_management.dto;

import jakarta.validation.constraints.NotBlank;

public record SchoolRequest(@NotBlank String name, @NotBlank String address) {}
