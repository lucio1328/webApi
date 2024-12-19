package com.web.api.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "token_user", schema = "public", catalog = "api")
public class TokenUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token_user", nullable = false)
    private int idTokenUser;
    @Basic
    @Column(name = "date_creation", nullable = false)
    private Timestamp dateCreation;
    @Basic
    @Column(name = "id_utilisateur", nullable = false)
    private int idUtilisateur;

    @Basic
    @Column(name = "token", nullable = false, length = 255)
    private String token;


    public int getIdTokenUser() {
        return idTokenUser;
    }

    public void setIdTokenUser(int idTokenUser) {
        this.idTokenUser = idTokenUser;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenUserEntity that = (TokenUserEntity) o;
        return idTokenUser == that.idTokenUser && idUtilisateur == that.idUtilisateur && Objects.equals(dateCreation, that.dateCreation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTokenUser, dateCreation, idUtilisateur);
    }
}
