package com.web.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.api.models.StatutEntity;

public interface StatutRepository extends JpaRepository<StatutEntity, Integer> {
}
