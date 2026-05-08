package com.tp3.portailetudiants.service;

import com.tp3.portailetudiants.model.Event;
import com.tp3.portailetudiants.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            eventRepository.save(new Event("event.1.title", "event.1.description", "event.1.location", "15 Mai 2026"));
            eventRepository.save(new Event("event.2.title", "event.2.description", "event.2.location", "18 Mai 2026"));
            eventRepository.save(new Event("event.3.title", "event.3.description", "event.3.location", "02 Juin 2026"));
            eventRepository.save(new Event("event.4.title", "event.4.description", "event.4.location", "25 Juin 2026"));

            System.out.println("--- Données insérées avec succès dans MySQL ! ---");
        } else {
            migrateLegacyEventContentToMessageKeys();
        }
    }

    private void migrateLegacyEventContentToMessageKeys() {
        Map<String, String[]> legacyToKeys = new HashMap<>();
        legacyToKeys.put("Conférence IA & Java", new String[]{"event.1.title", "event.1.description", "event.1.location"});
        legacyToKeys.put("Atelier Docker", new String[]{"event.2.title", "event.2.description", "event.2.location"});
        legacyToKeys.put("Hackathon 24h", new String[]{"event.3.title", "event.3.description", "event.3.location"});
        legacyToKeys.put("Soirée Gala", new String[]{"event.4.title", "event.4.description", "event.4.location"});

        List<Event> events = eventRepository.findAll();
        for (Event currentEvent : events) {
            String currentTitle = currentEvent.getTitre();
            if (currentTitle != null && currentTitle.startsWith("event.")) {
                continue;
            }

            String[] keys = legacyToKeys.get(currentTitle);
            if (keys != null) {
                currentEvent.setTitreKey(keys[0]);
                currentEvent.setDescriptionKey(keys[1]);
                currentEvent.setLieuKey(keys[2]);
                eventRepository.save(currentEvent);
            }
        }
    }
}
