package com.tp3.portailetudiants.repository;

import com.tp3.portailetudiants.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Pour la fonctionnalité "Recherche d'événements" demandée dans l'énoncé
    // ContainingIgnoreCase permet de chercher une partie du titre sans se soucier des majuscules
    // SQL équivalent : SELECT * FROM events WHERE titre LIKE %?%
    List<Event> findByTitreContainingIgnoreCase(String titre);
}