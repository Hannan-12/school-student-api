package com.example.demoschool_product_management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demoschool_product_management.entity.School;

public interface SchoolRepository extends MongoRepository<School, String> {}
