package com.web.api.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.web.api.models.Config;
import com.web.api.models.PreInscriptionEntity;
import com.web.api.models.Sessions;
import com.web.api.models.TokenUserEntity;
import com.web.api.models.UtilisateurEntity;
import com.web.api.repository.ConfigRepository;
import com.web.api.repository.TokenUserRepository;
import com.web.api.repository.UtilisateurRepository;
import com.web.api.service.AuthService;
import com.web.api.service.EmailSender;
import com.web.api.service.PinService;
import com.web.api.service.TentativeService;
import com.web.api.service.TokenUserService;
import com.web.api.service.UtilisateurService;

import jakarta.servlet.http.HttpSession;

@RestController
public class UtilisateurController {

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    AuthService authService;

    @Autowired
    TokenUserRepository tokenUserRepository;

    @Autowired
    TokenUserService tokenUserService;

    @Autowired
    EmailSender emailSender;

    @Autowired
    TentativeService tentativeService;

    @Autowired
    PinService pinService;

    @Autowired
    ConfigRepository configRepository;

    @Autowired
    HttpSession session;


    @PostMapping("check-login")
    public ResponseEntity<?> checkLogin(@RequestParam String email, @RequestParam String motDePasse) {
        if (tentativeService.isBlocked(email)) {
            this.emailSender.envoyer_email_reinitialiser_tentative(email,
                    "http://localhost:8081/api/reinitialiser/" + email);

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "error", "Votre compte a été temporairement bloqué, checkez votre email pour reinitialiser"));
        }
        try {

            UtilisateurEntity utilisateur = this.authService.checkLogin(email, motDePasse);

            String secretKey = "cleTokenUser";
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String token = JWT.create()
                    .withIssuer("CloudS5Key")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                    .sign(algorithm);

            // Sauvegarder le token dans la base de donnee:
            TokenUserEntity tokenUser = new TokenUserEntity();
            tokenUser.setIdUtilisateur(utilisateur.getIdUtilisateur());
            tokenUser.setDateCreation(new Timestamp(System.currentTimeMillis()));
            tokenUser.setToken(token);
            System.out.println("tokenUser = " + tokenUser.getIdUtilisateur());
            tokenUser = this.tokenUserService.saveToken(tokenUser);

            int PIN = (int) (Math.random() * 9000) + 1000;
            // Envoyer le code pin ici
            this.emailSender.envoyer_email_confirmation(email, String.valueOf(PIN), utilisateur.getIdUtilisateur());

            this.tentativeService.loginSucceeded(email);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of(
                            "message", "Authentification reussi, on vous a envoyé un code PIN pour vous connecter",
                            "data", utilisateur));

        } catch (Exception e) {
            // Faire tous les trucs de Lucio ici..
            tentativeService.loginFailed(email);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "error", e.getMessage()));
        }
    }

    @PostMapping("/pre-inscription")
    public ResponseEntity<?> preInscription(@RequestParam String nom, @RequestParam String email,
            @RequestParam String motDePasse, @RequestParam String confirmMotDePasse) {

        PreInscriptionEntity preInscriptionEntity = null;
        String message = "En attente de validation, checkez votre email";
        try {
            preInscriptionEntity = this.authService.controlPreInscription(nom, email, motDePasse, confirmMotDePasse);

            // Mandefa email validation:
            this.emailSender.envoyer_email_validation_inscription(email,
                    "http://localhost:8081/api/preinscriptions/confirmer/"
                            + preInscriptionEntity.getIdPreInscription());

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                    "message", message,
                    "data", preInscriptionEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "error", e.getMessage()));

        }

    }

    @GetMapping("saveUser")
    public ResponseEntity<?> save() {
        UtilisateurEntity user = new UtilisateurEntity();
        user.setNom("Jean");
        user.setEmail("jean@exemple.com");
        user.setMotDePasse(BCrypt.hashpw("12345", BCrypt.gensalt()));

        utilisateurRepository.save(user);
        return ResponseEntity.ok("saved");
    }

    // @GetMapping("get-user-from-cookie")
    // public ResponseEntity<?> getUserFromCookie(@CookieValue(value = "jwt_token", defaultValue = "") String jwtToken) {
    //     if (jwtToken.isEmpty()) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
    //                 "error", "Token manquant ou invalide"));
    //     }

    //     try {
    //         String secretKey = "cleTokenUser"; // La même clé utilisée pour signer le token
    //         Algorithm algorithm = Algorithm.HMAC256(secretKey);
    //         DecodedJWT decodedJWT = JWT.require(algorithm)
    //                 .withIssuer("CloudS5")
    //                 .build()
    //                 .verify(jwtToken);

    //         String email = decodedJWT.getSubject(); // Récupère l'email du token

    //         return ResponseEntity.ok(Map.of(
    //                 "message", "Token valide",
    //                 "email", email));
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
    //                 "error", "Token invalide ou expiré"));
    //     }
    // }

    @GetMapping("/parametrer-session")
    public ResponseEntity<?> parametrer(@RequestParam int dureeToken) {
        try {
            Config conf = this.configRepository.findById(1).get();
            conf.setDuree(dureeToken);

            this.configRepository.save(conf);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                    "message", "Duree token changé"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/reinitialiser/{email}")
    public ResponseEntity<?> reinitialiser(@PathVariable String email) {
        this.tentativeService.reinitialiser(email);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(Map.of(
                "message", "Votre compte a été réinitialisé. Vous pouvez reconnecter"));
    }

    @GetMapping("/confirmerConnexion/{idUtilisateur}/{pin}")
    public ResponseEntity<?> confirmerConnexion(@PathVariable int idUtilisateur, @PathVariable int pin) {
        String secretKey = "cleTokenUser";
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withIssuer("CloudS5Key")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                .sign(algorithm);

        // Controler de PIN
        try {
            this.pinService.verifierPin(pin, idUtilisateur);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(Map.of(
                    "error", e.getMessage()));
        }

        // Sauvegarder le token dans la base de donnee:
        TokenUserEntity tokenUser = new TokenUserEntity();
        tokenUser.setIdUtilisateur(idUtilisateur);
        tokenUser.setDateCreation(new Timestamp(System.currentTimeMillis()));
        tokenUser.setToken(token);
        System.out.println("tokenUser = " + tokenUser.getIdUtilisateur());
        tokenUser = this.tokenUserService.saveToken(tokenUser);

        session.setAttribute("utilisateur", idUtilisateur);

        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(Map.of(
                        "message", "Authentification reussi, vous etes connecté maintenant"));
    }

    @PutMapping("/{Id_utilisateur}")
    public ResponseEntity<?> updateUtilisateur(
            @PathVariable int Id_utilisateur,
            @RequestBody UtilisateurEntity utilisateurDetails) {
        if (this.tokenUserService.verifierExpirationToken(Id_utilisateur)) {
            this.utilisateurService.deconnecter(Id_utilisateur);
            return ResponseEntity.status(HttpStatus.GONE).body(Map.of(
                "error", "Votre session a expiré"
            ));

        }
        UtilisateurEntity updatedUtilisateur = utilisateurService.updateUtilisateur(Id_utilisateur, utilisateurDetails);
        if (updatedUtilisateur != null) {
            return ResponseEntity.ok(updatedUtilisateur); // Retourner l'utilisateur mis à jour
        }
        return ResponseEntity.notFound().build(); // Retourner 404 si l'utilisateur n'existe pas
    }

    @DeleteMapping("/{Id_utilisateur}")
    public ResponseEntity<?> deleteUtilisateur(@PathVariable int Id_utilisateur) {
        boolean isDeleted = utilisateurService.deleteUtilisateur(Id_utilisateur);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatusCode.valueOf(204)).body(Map.of(
                    "message", "Utilisateur supprimé avec succes"));
        }
        return ResponseEntity.notFound().build();
    }


}
