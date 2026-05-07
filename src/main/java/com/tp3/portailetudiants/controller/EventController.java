package com.tp3.portailetudiants.controller;

import com.tp3.portailetudiants.repository.EventRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/events")
    public String searchEvents(@RequestParam(required = false) String keyword, HttpSession session, Model model) {
        // SECURITÉ : Vérifier si l'étudiant est connecté
        if (session.getAttribute("userSession") == null) {
            return "redirect:/login";
        }

        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("events", eventRepository.findByTitreContaining(keyword));
        } else {
            model.addAttribute("events", eventRepository.findAll());
        }
        return "events"; // Thymeleaf avec support multilingue
    }
}