package com.example.demoschool_product_management.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record StudentRequest(
        @NotBlank String name,
        @Min(1) @Max(120) int age,
        @NotBlank String schoolId) {}
