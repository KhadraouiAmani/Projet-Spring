package com.tp3.portailetudiants.service;

import com.tp3.portailetudiants.model.Event;
import com.tp3.portailetudiants.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer {

    @Autowired
    private EventRepository eventRepository;

    /**
     * Cette méthode se lance automatiquement dès que Spring a fini de démarrer.
     * Elle vérifie si la base est vide avant d'ajouter des données pour éviter les doublons.
     */
    @EventListener
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // On vérifie si la table est vide pour ne pas rajouter les mêmes événements à chaque redémarrage
        if (eventRepository.count() == 0) {

            System.out.println("--- AMANI ARCHITECT : Initialisation des données de test ---");

            // Création de quelques événements pour l'INSAT
            eventRepository.save(new Event("Conférence IA & Java", "Découvrez Spring 6 et l'IA.", "Amphi A", "15 Mai 2026"));
            eventRepository.save(new Event("Atelier Docker", "Apprenez à conteneuriser vos apps.", "Salle 102", "18 Mai 2026"));
            eventRepository.save(new Event("Hackathon 24h", "Compétition de code non-stop.", "Bibliothèque", "02 Juin 2026"));
            eventRepository.save(new Event("Soirée Gala", "Célébration de fin d'année.", "Esplanade", "25 Juin 2026"));

            System.out.println("--- Données insérées avec succès dans MySQL ! ---");
        }
    }
}