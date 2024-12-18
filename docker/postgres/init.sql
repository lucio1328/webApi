CREATE TABLE utilisateur(
   Id_utilisateur SERIAL,
   nom VARCHAR(50)  NOT NULL,
   email VARCHAR(50)  NOT NULL,
   mot_de_passe VARCHAR(50)  NOT NULL,
   PRIMARY KEY(Id_utilisateur)
);

CREATE TABLE statut(
   Id_statut SERIAL,
   libelle VARCHAR(50)  NOT NULL,
   PRIMARY KEY(Id_statut),
   UNIQUE(libelle)
);

CREATE TABLE validation_connection(
   Id_validation_connection SERIAL,
   pin VARCHAR(50)  NOT NULL,
   daty TIMESTAMP NOT NULL,
   Id_statut INTEGER NOT NULL,
   Id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(Id_validation_connection),
   FOREIGN KEY(Id_statut) REFERENCES statut(Id_statut),
   FOREIGN KEY(Id_utilisateur) REFERENCES utilisateur(Id_utilisateur)
);

CREATE TABLE token_user(
   Id_token_user SERIAL,
   date_creation TIMESTAMP NOT NULL,
   Id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(Id_token_user),
   UNIQUE(Id_utilisateur),
   FOREIGN KEY(Id_utilisateur) REFERENCES utilisateur(Id_utilisateur)
);

CREATE TABLE pre_inscription(
   Id_pre_inscription SERIAL,
   nom VARCHAR(50)  NOT NULL,
   email VARCHAR(50)  NOT NULL,
   mot_de_passe VARCHAR(50)  NOT NULL,
   Id_statut INTEGER NOT NULL,
   PRIMARY KEY(Id_pre_inscription),
   FOREIGN KEY(Id_statut) REFERENCES statut(Id_statut)
);
