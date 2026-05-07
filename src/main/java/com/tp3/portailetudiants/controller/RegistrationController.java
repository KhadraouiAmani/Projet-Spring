package com.tp3.portailetudiants.controller;

import com.tp3.portailetudiants.model.User;
import com.tp3.portailetudiants.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    // Injection automatique de ton Repository pour communiquer avec MySQL
    @Autowired
    private UserRepository userRepository;

    /**
     * Affiche le formulaire d'inscription (GET)
     * On prépare un objet User vide pour que Thymeleaf puisse s'y lier.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Doit correspondre au fichier register.html ou register.jsp de Balkis
    }

    /**
     * Traite l'inscription (POST)
     * C'est ici que les Exemples 5 et 6 sont réalisés.
     */
    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

        // --- EXEMPLE 5 : VALIDATION DES CHAMPS ---
        // On vérifie si l'email existe déjà avant de valider le reste
        if (userRepository.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Cet email est déjà utilisé");
        }

        // Si le BindingResult contient des erreurs (ex: email invalide, nom vide)
        if (result.hasErrors()) {
            return "register"; // On renvoie l'utilisateur sur le formulaire pour corriger
        }

        // --- EXEMPLE 6 : HIBERNATE / PERSISTANCE ---
        // Si tout est valide, Hibernate enregistre l'objet en une ligne SQL INSERT
        userRepository.save(user);

        // On redirige vers la page de login pour qu'Islem prenne le relais (Session)
        return "redirect:/login";
    }
}