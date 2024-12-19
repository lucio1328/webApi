package com.web.api.service;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.models.PreInscriptionEntity;
import com.web.api.models.UtilisateurEntity;
import com.web.api.repository.PreInscriptionRepository;
import com.web.api.repository.UtilisateurRepository;

@Service
public class AuthService {


    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private PreInscriptionRepository preInscriptionRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    public static String hashPassword(String motDePasse) {
        return BCrypt.hashpw(motDePasse, BCrypt.gensalt());
    }

    public boolean checkUtilisateurByEmail(String email) {
        Optional<UtilisateurEntity> utilisateur = this.utilisateurRepository.findByEmail(email);
    
        return utilisateur.isPresent();
    }

    public PreInscriptionEntity controlPreInscription(String nom, String email, String motDePasse,
            String confirmPassword) throws Exception {

        // Verifier si email existe deja
        if (this.checkUtilisateurByEmail(email)) {
            throw new Exception("L'email que vous avez saisi a déjà un compte");
        }

        // Verifier le mot de passe
        if (!motDePasse.equals(confirmPassword)) {
            throw new Exception("Verifier bien les mots de passes");
        }

        // Hasher le mot de passe
        String hashMotDePasse = hashPassword(motDePasse);

        PreInscriptionEntity preInscription = new PreInscriptionEntity();
        preInscription.setNom(nom);
        preInscription.setEmail(email);
        preInscription.setMotDePasse(hashMotDePasse);
        preInscription.setIdStatut(1);

        return this.preInscriptionRepository.save(preInscription);

    }

    public UtilisateurEntity checkLogin(String email, String motDePasse) throws Exception {
        Optional<UtilisateurEntity> utilisateurOpt = this.utilisateurRepository.findByEmail(email);
        
        if (utilisateurOpt.isEmpty()) {
            throw new Exception("Email incorrect");
        }
        
        UtilisateurEntity utilisateur = utilisateurOpt.get();
    
        if (!BCrypt.checkpw(motDePasse, utilisateur.getMotDePasse())) {
            throw new Exception("Mot de passe incorrect");
        }
    
        return utilisateur;
    }
    
}
