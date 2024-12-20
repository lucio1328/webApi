package com.web.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.models.UtilisateurEntity;
import com.web.api.repository.TokenUserRepository;
import com.web.api.repository.UtilisateurRepository;
import com.web.api.repository.ValidationConnectionRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private TokenUserRepository tokenUserRepository;

    @Autowired
    HttpSession session;


    public UtilisateurEntity updateUtilisateur(int Id_utilisateur, UtilisateurEntity utilisateurDetails) {
        Optional<UtilisateurEntity> utilisateurOpt = utilisateurRepository.findById(Id_utilisateur);
        if (utilisateurOpt.isPresent()) {
            UtilisateurEntity utilisateur = utilisateurOpt.get();

            utilisateur.setNom(utilisateurDetails.getNom());
            utilisateur.setMotDePasse(AuthService.hashPassword(utilisateurDetails.getMotDePasse()));

            // utilisateur.setEmail(utilisateurDetails.getEmail()); // Cette ligne est
            // commentée pour ne pas mettre à jour l'email

            return utilisateurRepository.save(utilisateur); // Enregistrer l'utilisateur mis à jour
        }
        return null;
    }

    public boolean deleteUtilisateur(int Id_utilisateur) {
        Optional<UtilisateurEntity> utilisateurOpt = utilisateurRepository.findById(Id_utilisateur);
        if (utilisateurOpt.isPresent()) {
            // tokenUserRepository.deleteByIdUtilisateur(Id_utilisateur);
            // validationConnectionRepository.deleteByIdUtilisateur(Id_utilisateur);

            utilisateurRepository.delete(utilisateurOpt.get()); // Supprimer l'utilisateur
            return true;
        }
        return false; // Retourner false si l'utilisateur n'est pas trouvé
    }

    public boolean deconnecter(int idUtilisateur) {
        // supprimer le token dans la base de donnee
        this.tokenUserRepository.deleteByIdUtilisateur(idUtilisateur);

        // supprimer session
        session.invalidate();

        return true;

    }
}
