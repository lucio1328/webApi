package com.web.api.service;

import com.web.api.models.UtilisateurEntity;
import com.web.api.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    public UtilisateurEntity updateUtilisateur(int Id_utilisateur, UtilisateurEntity utilisateurDetails) {
        Optional<UtilisateurEntity> utilisateurOpt = utilisateurRepository.findById(Id_utilisateur);
        if (utilisateurOpt.isPresent()) {
            UtilisateurEntity utilisateur = utilisateurOpt.get();
            
            utilisateur.setNom(utilisateurDetails.getNom());
            utilisateur.setMot_de_passe(utilisateurDetails.getMot_de_passe());
    
         
            // utilisateur.setEmail(utilisateurDetails.getEmail());  // Cette ligne est commentée pour ne pas mettre à jour l'email
            
            return utilisateurRepository.save(utilisateur);  // Enregistrer l'utilisateur mis à jour
        }
        return null; 
    }
    

    public boolean deleteUtilisateur(int Id_utilisateur) {
        Optional<UtilisateurEntity> utilisateurOpt = utilisateurRepository.findById(Id_utilisateur);
        if (utilisateurOpt.isPresent()) {
            utilisateurRepository.delete(utilisateurOpt.get());  // Supprimer l'utilisateur
            return true;
        }
        return false;  // Retourner false si l'utilisateur n'est pas trouvé
    }
    
    
}
