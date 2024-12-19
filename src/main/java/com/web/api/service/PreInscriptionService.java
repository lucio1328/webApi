package com.web.api.service;

import com.web.api.models.PreInscriptionEntity;
import com.web.api.models.UtilisateurEntity;
import com.web.api.repository.PreInscriptionRepository;
import com.web.api.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreInscriptionService {

    @Autowired
    private PreInscriptionRepository preInscriptionRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public boolean confirmerPreInscription(int Id_pre_inscription) {
        // Récupérer l'enregistrement de pre_inscription
        PreInscriptionEntity preInscription = preInscriptionRepository.findById(Id_pre_inscription).orElse(null);

        if (preInscription != null && preInscription.getIdStatut() == 1) { // Vérifie que le statut est "en attente"
            // Mettre à jour le statut en "confirmée" (par exemple, idStatut = 2)
            preInscription.setIdStatut(2);
            preInscriptionRepository.save(preInscription);

            // Insérer les valeurs dans la table utilisateur
            UtilisateurEntity utilisateur = new UtilisateurEntity();
            utilisateur.setNom(preInscription.getNom());
            utilisateur.setEmail(preInscription.getEmail());
            utilisateur.setMotDePasse(preInscription.getMotDePasse());
            utilisateurRepository.save(utilisateur);

            return true; // Succès
        }

        return false; // Échec (non trouvé ou statut incorrect)
    }
}
