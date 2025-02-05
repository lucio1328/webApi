package com.web.api.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.web.api.models.PreInscriptionEntity;
import com.web.api.models.UtilisateurEntity;
import com.web.api.repository.PreInscriptionRepository;
import com.web.api.repository.UtilisateurRepository;

@Service
public class PreInscriptionService {

    @Autowired
    private PreInscriptionRepository preInscriptionRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private final RestTemplate restTemplate = new RestTemplate();

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
            notifyP2(utilisateur.getNom(), utilisateur.getEmail());
            return true; // Succès
        }

        return false; // Échec (non trouvé ou statut incorrect)
    }

    private void notifyP2(String name, String email) {
        try {
            String webhookUrl = "http://localhost:8000/user-validated";

            // Encoder les paramètres
            String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
            String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString());

            String fullUrl = webhookUrl + "?name=" + encodedName + "&email=" + email;
            System.out.println("URL WEBHOOK = " + fullUrl);

            ResponseEntity<String> response = restTemplate.getForEntity(fullUrl, String.class);
            System.out.println("Notification envoyée à P2: " + response);
        } catch (Exception e) {
            System.err.println("Erreur lors de la notification P2 : " + e.getMessage());
        }
    }

    public void initializeCsrfToken() {
        String csrfUrl = "http://localhost:8000/sanctum/csrf-cookie";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(csrfUrl, String.class);
            System.out.println("CSRF token récupéré avec succès : " + response.getBody());
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération du CSRF token : " + e.getMessage());
        }
    }

}
