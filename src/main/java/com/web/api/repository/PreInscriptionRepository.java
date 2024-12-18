package com.web.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.api.models.PreInscriptionEntity;

public interface PreInscriptionRepository extends JpaRepository<PreInscriptionEntity, Integer> {
}
