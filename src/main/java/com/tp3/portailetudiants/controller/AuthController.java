package com.tp3.portailetudiants.controller;

import com.tp3.portailetudiants.model.User;
import com.tp3.portailetudiants.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    // EXEMPLE 1 : Utilisation de JSP pour le Login
    @GetMapping("/login")
    public String showLogin() {
        return "login"; // Ouvre WEB-INF/jsp/login.jsp
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password, HttpSession session, Model model) {
        User user = authService.login(email, password);
        if (user != null) {
            session.setAttribute("userSession", user); // GESTION DE SESSION
            return "redirect:/events";
        }
        model.addAttribute("error", "Email ou mot de passe incorrect");
        return "login";
    }

    // EXEMPLE 2 & 5 : Thymeleaf + Validation
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Ouvre WEB-INF/templates/register.html
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "register"; // Retourne au formulaire si erreurs (Validation)
        }
        authService.register(user);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Détruit la session
        return "redirect:/login";
    }
}