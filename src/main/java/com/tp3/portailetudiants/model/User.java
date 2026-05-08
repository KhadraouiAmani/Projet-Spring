package com.tp3.portailetudiants.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{form.validation.required}")
    @Pattern(
            regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ'-]+(\\s+[A-Za-zÀ-ÖØ-öø-ÿ'-]+)+$",
            message = "{error.name.empty}"
    )
    private String nom;

    @Email(message = "{form.validation.email}")
    @NotBlank(message = "{form.validation.required}")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@insat\\.ucar\\.tn$",
            message = "{form.validation.email}"
    )
    @Column(unique = true)
    private String email;

    @Size(min = 8, message = "{form.validation.password.weak}")
    private String password;

    public User() {
    }

    public User(String nom, String email, String password) {
        this.nom = nom;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
