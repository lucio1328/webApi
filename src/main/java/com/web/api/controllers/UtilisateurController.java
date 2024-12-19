package com.web.api.controllers;

import com.web.api.models.UtilisateurEntity;
import com.web.api.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PutMapping("/{Id_utilisateur}")
    public ResponseEntity<UtilisateurEntity> updateUtilisateur(
            @PathVariable int Id_utilisateur,
            @RequestBody UtilisateurEntity utilisateurDetails) {

        UtilisateurEntity updatedUtilisateur = utilisateurService.updateUtilisateur(Id_utilisateur, utilisateurDetails);
        if (updatedUtilisateur != null) {
            return ResponseEntity.ok(updatedUtilisateur); // Retourner l'utilisateur mis à jour
        }
        return ResponseEntity.notFound().build(); // Retourner 404 si l'utilisateur n'existe pas
    }

    @DeleteMapping("/{Id_utilisateur}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable int Id_utilisateur) {
        boolean isDeleted = utilisateurService.deleteUtilisateur(Id_utilisateur);

        if (isDeleted) {
            return ResponseEntity.noContent().build(); // Retourner 204 si la suppression a réussi
        }
        return ResponseEntity.notFound().build(); // Retourner 404 si l'utilisateur n'existe pas
    }
}
