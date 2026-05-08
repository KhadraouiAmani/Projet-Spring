package com.tp3.portailetudiants.controller;

import com.tp3.portailetudiants.model.Event;
import com.tp3.portailetudiants.repository.EventRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/events")
    public String searchEvents(@RequestParam(name = "keyword", required = false) String keyword,
                               @RequestParam(name = "type", required = false) String type,
                               HttpSession session,
                               Model model) {
        if (session.getAttribute("userSession") == null) {
            return "redirect:/login";
        }

        String normalizedKeyword = keyword == null ? "" : keyword.trim();
        String normalizedType = type == null ? "" : type.trim();
        List<Event> events = normalizedKeyword.isEmpty()
                ? eventRepository.findAll()
                : eventRepository.findByTitreContainingIgnoreCase(normalizedKeyword);

        Set<Long> joinedEventIds = getJoinedEventIds(session);
        List<EventCard> eventCards = events.stream()
                .map(event -> toCard(event, joinedEventIds.contains(event.getId())))
                .filter(card -> normalizedType.isEmpty() || card.typeKey().equals(normalizedType))
                .toList();

        model.addAttribute("keyword", normalizedKeyword);
        model.addAttribute("selectedType", normalizedType);
        model.addAttribute("eventCards", eventCards);
        return "index";
    }

    @PostMapping("/events/{id}/join")
    public String joinEvent(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("userSession") == null) {
            return "redirect:/login";
        }
        getJoinedEventIds(session).add(id);
        return "redirect:/events";
    }

    @PostMapping("/events/{id}/leave")
    public String leaveEvent(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("userSession") == null) {
            return "redirect:/login";
        }
        getJoinedEventIds(session).remove(id);
        return "redirect:/events";
    }

    @SuppressWarnings("unchecked")
    private Set<Long> getJoinedEventIds(HttpSession session) {
        Object joinedEvents = session.getAttribute("joinedEventIds");
        if (joinedEvents instanceof Set<?>) {
            return (Set<Long>) joinedEvents;
        }

        Set<Long> joinedEventIds = new LinkedHashSet<>();
        session.setAttribute("joinedEventIds", joinedEventIds);
        return joinedEventIds;
    }

    private EventCard toCard(Event event, boolean joined) {
        String typeKey = resolveTypeKey(event);
        return new EventCard(
                event.getId(),
                event.getTitre(),
                event.getDescription(),
                event.getDate(),
                resolveTime(typeKey),
                event.getLieu(),
                typeKey,
                resolveCapacity(typeKey),
                joined
        );
    }

    private String resolveTypeKey(Event event) {
        String title = event.getTitre() == null ? "" : event.getTitre().toLowerCase(Locale.ROOT);
        if (title.contains("conf") || title.contains("conference")) {
            return "eventtype.conference";
        }
        if (title.contains("atelier") || title.contains("workshop")) {
            return "eventtype.workshop";
        }
        if (title.contains("hackathon") || title.contains("semin")) {
            return "eventtype.seminar";
        }
        if (title.contains("soir") || title.contains("party") || title.contains("gala")) {
            return "eventtype.party";
        }
        return "eventtype.other";
    }

    private String resolveTime(String typeKey) {
        return switch (typeKey) {
            case "eventtype.conference" -> "09:00";
            case "eventtype.workshop" -> "14:00";
            case "eventtype.seminar" -> "10:30";
            case "eventtype.party" -> "18:30";
            default -> "12:00";
        };
    }

    private int resolveCapacity(String typeKey) {
        return switch (typeKey) {
            case "eventtype.conference" -> 180;
            case "eventtype.workshop" -> 60;
            case "eventtype.seminar" -> 90;
            case "eventtype.party" -> 220;
            default -> 75;
        };
    }

    private record EventCard(Long id,
                             String title,
                             String description,
                             String date,
                             String time,
                             String location,
                             String typeKey,
                             int capacity,
                             boolean joined) {
    }
}
