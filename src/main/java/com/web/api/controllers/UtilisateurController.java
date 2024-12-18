package com.web.api.controllers;

import com.web.api.models.UtilisateurEntity;
import com.web.api.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UtilisateurController {

    private UtilisateurService utilisateurService;

    @GetMapping("/{id}")
    public UtilisateurEntity getUtilisateurById(@PathVariable Integer id) {
        return utilisateurService.get_by_id(id);
    }

    @PutMapping("/{id}")
    public String updateUtilisateur(@PathVariable Integer id, @RequestBody UtilisateurEntity utilisateur) {
        UtilisateurEntity existingUtilisateur = utilisateurService.get_by_id(id);
        if (existingUtilisateur != null) {
            utilisateur.setIdUtilisateur(id);
            utilisateurService.save(utilisateur);
            return "Utilisateur updated successfully.";
        }
        else {
            return "Utilisateur not found.";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUtilisateur(@PathVariable Integer id) {
        utilisateurService.delete_by_id(id);
        return "Utilisateur deleted successfully.";
    }

}
