package com.tp3.portailetudiants.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    private String description;
    private String lieu;
    private String date;

    // Constructeurs
    public Event() {}

    public Event(String titre, String description, String lieu, String date) {
        this.titre = titre;
        this.description = description;
        this.lieu = lieu;
        this.date = date;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}