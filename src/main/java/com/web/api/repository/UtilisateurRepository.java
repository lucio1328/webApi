package com.web.api.repository;

import com.web.api.models.UtilisateurEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity, Integer> {
    Optional<UtilisateurEntity> findByEmail(String email);

    Optional<UtilisateurEntity> findByEmailAndMotDePasse(String email, String motDePasse);

}
