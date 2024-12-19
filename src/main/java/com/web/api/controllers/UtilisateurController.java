package com.web.api.controllers;

import java.util.Date;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.web.api.models.PreInscriptionEntity;
import com.web.api.models.TokenUserEntity;
import com.web.api.models.UtilisateurEntity;
import com.web.api.repository.TokenUserRepository;
import com.web.api.repository.UtilisateurRepository;
import com.web.api.services.AuthService;
import com.web.api.services.UtilisateurService;

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

    @PostMapping("check-login")
    public ResponseEntity<?> checkLogin(@RequestParam String email, @RequestParam String motDePasse) {
        try {
            UtilisateurEntity utilisateur = this.authService.checkLogin(email, motDePasse);

            String secretKey = "cleTokenUser";
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String token = JWT.create()
                    .withIssuer("CloudS5")
                    .withSubject(email)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                    .sign(algorithm);

            ResponseCookie jwtCookie = ResponseCookie.from("jwt_token", token)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(3600)
                    .sameSite("Strict")
                    .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .header("Set-Cookie", jwtCookie.toString())
                    .body(Map.of(
                            "message", "Utilisateur trouvé",
                            "data", utilisateur));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "error", e.getMessage()));
        }
    }

    @PostMapping("/pre-inscription")
    public ResponseEntity<?> preInscription(@RequestParam String nom, @RequestParam String email,
            @RequestParam String motDePasse, @RequestParam String confirmMotDePasse) {

        PreInscriptionEntity preInscriptionEntity = null;
        String message = "En attente de validation";
        try {
            preInscriptionEntity = this.authService.controlPreInscription(nom, email, motDePasse, confirmMotDePasse);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                    "message", message,
                    "data", preInscriptionEntity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "error", e.getStackTrace()));

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

    @GetMapping("get-user-from-cookie")
    public ResponseEntity<?> getUserFromCookie(@CookieValue(value = "jwt_token", defaultValue = "") String jwtToken) {
        if (jwtToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "error", "Token manquant ou invalide"));
        }

        try {
            String secretKey = "cleTokenUser"; // La même clé utilisée pour signer le token
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("CloudS5")
                    .build()
                    .verify(jwtToken);

            String email = decodedJWT.getSubject(); // Récupère l'email du token

            return ResponseEntity.ok(Map.of(
                    "message", "Token valide",
                    "email", email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "error", "Token invalide ou expiré"));
        }
    }

}
