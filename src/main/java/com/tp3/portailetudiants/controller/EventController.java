package com.tp3.portailetudiants.controller;

import com.tp3.portailetudiants.repository.EventRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.tp3.portailetudiants.model.Event; // Pour que le contrôleur connaisse ta classe Event
import org.springframework.web.bind.annotation.PathVariable; // Pour utiliser @PathVariable

import java.util.List;
import java.util.Locale;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/events")
    public String searchEvents(@RequestParam(name = "keyword", required = false) String keyword, HttpSession session, Model model) {
        // SECURITÉ : Vérifier si l'étudiant est connecté
        if (session.getAttribute("userSession") == null) {
            return "redirect:/login";
        }

        Locale locale = (Locale) session.getAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE");
        if (locale == null) {
            locale = Locale.FRENCH;
        }
        final Locale currentLocale = locale;

        List<Event> allEvents = eventRepository.findAll();
        if (keyword != null && !keyword.isBlank()) {
            String lowercaseKeyword = keyword.toLowerCase(currentLocale);
            List<Event> filteredEvents = allEvents.stream()
                    .filter(currentEvent -> {
                        String title = messageSource.getMessage(currentEvent.getTitre(), null, currentEvent.getTitre(), currentLocale);
                        String description = messageSource.getMessage(currentEvent.getDescription(), null, currentEvent.getDescription(), currentLocale);
                        String location = messageSource.getMessage(currentEvent.getLieu(), null, currentEvent.getLieu(), currentLocale);
                        return title.toLowerCase(currentLocale).contains(lowercaseKeyword)
                                || description.toLowerCase(currentLocale).contains(lowercaseKeyword)
                                || location.toLowerCase(currentLocale).contains(lowercaseKeyword);
                    })
                    .toList();
            model.addAttribute("events", filteredEvents);
        } else {
            model.addAttribute("events", allEvents);
        }

        return "events"; // Thymeleaf avec support multilingue
    }
    @GetMapping("/events/participate/{id}")
    public String participate(@PathVariable("id") Long id, Model model, Locale locale) {
        // On cherche l'événement en base pour récupérer son titre
        Event event = eventRepository.findById(id).orElse(null);

        if (event != null) {
            String localizedTitle = messageSource.getMessage(event.getTitre(), null, event.getTitre(), locale);
            String successMessage = messageSource.getMessage(
                    "event.participation.success",
                    new Object[]{localizedTitle},
                    "Votre participation à l'événement '" + localizedTitle + "' a été prise en compte !",
                    locale
            );
            model.addAttribute("message", successMessage);
        }

        return "participation-confirm"; // On va créer cette petite page
    }
}
