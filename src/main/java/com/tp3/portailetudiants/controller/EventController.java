package com.tp3.portailetudiants.controller;

import com.tp3.portailetudiants.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/events")
    public String listEvents(Model model) {
        // On récupère tous les événements de la base MySQL
        model.addAttribute("events", eventRepository.findAll());
        return "events"; // On va créer le fichier events.html
    }
}