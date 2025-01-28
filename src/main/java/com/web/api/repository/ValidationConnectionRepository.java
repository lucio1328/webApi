package com.web.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.api.models.ValidationConnectionEntity;
import java.util.List;


public interface ValidationConnectionRepository extends JpaRepository<ValidationConnectionEntity, Integer> {

    ValidationConnectionEntity findTopByIdUtilisateur(int idUtilisateur);

    ValidationConnectionEntity findTopByPinAndIdUtilisateur(String pin, int idUtilisateur);

    @Query("DELETE FROM ValidationConnectionEntity vc WHERE vc.idUtilisateur = :idUtilisateur")
    void deleteByIdUtilisateur(@Param("idUtilisateur") int idUtilisateur);


}
