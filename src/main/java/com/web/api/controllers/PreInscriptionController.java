package com.web.api.controllers;

import com.web.api.service.PreInscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preinscriptions")
public class PreInscriptionController {

    @Autowired
    private PreInscriptionService preInscriptionService;

    @PostMapping("/confirmer/{Id_pre_inscription}")
    public ResponseEntity<String> confirmerPreInscription(@PathVariable int Id_pre_inscription) {
        boolean isConfirmed = preInscriptionService.confirmerPreInscription(Id_pre_inscription);

        if (isConfirmed) {
            return ResponseEntity.ok("Inscription confirmée et utilisateur créé avec succès !");
        } else {
            return ResponseEntity.badRequest().body("Échec de la confirmation de l'inscription.");
        }
    }
}

