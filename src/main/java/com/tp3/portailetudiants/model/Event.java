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
    @Column(name = "titre")
    private String titreKey;

    @Column(name = "description")
    private String descriptionKey;

    @Column(name = "lieu")
    private String lieuKey;

    private String date;

    // Constructeurs
    public Event() {}

    public Event(String titreKey, String descriptionKey, String lieuKey, String date) {
        this.titreKey = titreKey;
        this.descriptionKey = descriptionKey;
        this.lieuKey = lieuKey;
        this.date = date;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titreKey; }
    public void setTitre(String titre) { this.titreKey = titre; }

    public String getDescription() { return descriptionKey; }
    public void setDescription(String description) { this.descriptionKey = description; }

    public String getLieu() { return lieuKey; }
    public void setLieu(String lieu) { this.lieuKey = lieu; }

    public String getTitreKey() { return titreKey; }
    public void setTitreKey(String titreKey) { this.titreKey = titreKey; }

    public String getDescriptionKey() { return descriptionKey; }
    public void setDescriptionKey(String descriptionKey) { this.descriptionKey = descriptionKey; }

    public String getLieuKey() { return lieuKey; }
    public void setLieuKey(String lieuKey) { this.lieuKey = lieuKey; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
