package com.web.api.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "validation_connection", schema = "public", catalog = "api")
public class ValidationConnectionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_validation_connection", nullable = false)
    private int idValidationConnection;
    @Basic
    @Column(name = "pin", nullable = false, length = 50)
    private String pin;
    @Basic
    @Column(name = "daty", nullable = false)
    private Timestamp daty;
    @Basic
    @Column(name = "id_statut", nullable = false)
    private int idStatut;
    @Basic
    @Column(name = "id_utilisateur", nullable = false)
    private int idUtilisateur;

    public int getIdValidationConnection() {
        return idValidationConnection;
    }

    public void setIdValidationConnection(int idValidationConnection) {
        this.idValidationConnection = idValidationConnection;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Timestamp getDaty() {
        return daty;
    }

    public void setDaty(Timestamp daty) {
        this.daty = daty;
    }

    public int getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(int idStatut) {
        this.idStatut = idStatut;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationConnectionEntity that = (ValidationConnectionEntity) o;
        return idValidationConnection == that.idValidationConnection && idStatut == that.idStatut && idUtilisateur == that.idUtilisateur && Objects.equals(pin, that.pin) && Objects.equals(daty, that.daty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idValidationConnection, pin, daty, idStatut, idUtilisateur);
    }
}
