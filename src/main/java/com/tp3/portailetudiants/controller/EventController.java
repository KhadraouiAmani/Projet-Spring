package com.tp3.portailetudiants.controller;

import com.tp3.portailetudiants.repository.EventRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.tp3.portailetudiants.model.Event; // Pour que le contrôleur connaisse ta classe Event
import org.springframework.web.bind.annotation.PathVariable; // Pour utiliser @PathVariable

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/events")
    public String searchEvents(@RequestParam(name = "keyword", required = false) String keyword, HttpSession session, Model model) {
        // SECURITÉ : Vérifier si l'étudiant est connecté
        if (session.getAttribute("userSession") == null) {
            return "redirect:/login";
        }

        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("events", eventRepository.findByTitreContainingIgnoreCase(keyword));
        } else {
            model.addAttribute("events", eventRepository.findAll());
        }
        return "events"; // Thymeleaf avec support multilingue
    }
    @GetMapping("/events/participate/{id}")
    public String participate(@PathVariable("id") Long id, Model model) {
        // On cherche l'événement en base pour récupérer son titre
        Event event = eventRepository.findById(id).orElse(null);

        if (event != null) {
            model.addAttribute("message", "Votre participation à l'événement '" + event.getTitre() + "' a été prise en compte !");
        }

        return "participation-confirm"; // On va créer cette petite page
    }
}