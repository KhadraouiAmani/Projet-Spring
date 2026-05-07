package com.tp3.portailetudiants.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Pattern(
            regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ'-]+(\\s+[A-Za-zÀ-ÖØ-öø-ÿ'-]+)+$",
            message = "Veuillez saisir votre nom complet (Prénom et Nom séparés par un espace)"
    )
    private String nom;

    @Email(message = "L'email doit être valide")
    @NotBlank(message = "L'email est obligatoire")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@insat\\.ucar\\.tn$",
            message = "L'email doit être un email académique valide "
    )
    @Column(unique = true)
    private String email;

    @Size(min = 6, message = "Le mot de passe doit faire au moins 6 caractères")
    private String password;

    // Constructeurs
    public User() {}

    public User(String nom, String email, String password) {
        this.nom = nom;
        this.email = email;
        this.password = password;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}