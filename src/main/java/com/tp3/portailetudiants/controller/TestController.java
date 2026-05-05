package com.tp3.portailetudiants.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    // On teste la racine du site
    @GetMapping("/hello")
    public String index(Model model) {
        model.addAttribute("message", "BRAVO ! Spring 6 fonctionne !");
        return "welcome"; // Va chercher WEB-INF/jsp/welcome.jsp
    }
}