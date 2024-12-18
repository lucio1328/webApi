package com.web.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.api.models.UtilisateurEntity;

public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity, Integer> {
}
