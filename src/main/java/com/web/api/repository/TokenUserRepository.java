package com.web.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.api.models.TokenUserEntity;

public interface TokenUserRepository extends JpaRepository<TokenUserEntity, Integer> {
    Optional<TokenUserEntity> findByIdUtilisateur(int idUtilisateur);

    @Modifying
    @Query("DELETE FROM TokenUserEntity tk WHERE tk.idUtilisateur = :idUtilisateur")
    void deleteByIdUtilisateur(@Param("idUtilisateur") int idUtilisateur);

}
