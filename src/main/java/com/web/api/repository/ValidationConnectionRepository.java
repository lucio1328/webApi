package com.web.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.api.models.ValidationConnectionEntity;

public interface ValidationConnectionRepository extends JpaRepository<ValidationConnectionEntity, Integer> {
}
