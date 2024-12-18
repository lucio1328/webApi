package com.web.webapi.repository;

import com.web.webapi.models.UtilisateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity, Integer> {
}
