package com.tp3.portailetudiants.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    // Root path - displays welcome.html (Thymeleaf)
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "BRAVO ! Spring 6 fonctionne !");
        return "welcome"; // Returns WEB-INF/templates/welcome.html
    }

    // Mapping for test-xml.jsp
    @GetMapping("/test-xml")
    public String testXml() {
        return "test-xml"; // Returns WEB-INF/jsp/test-xml.jsp
    }

    // Mapping for welcome.jsp
    @GetMapping("/welcome-jsp")
    public String welcomeJsp(Model model) {
        model.addAttribute("message", "Welcome to JSP Page!");
        return "welcome"; // Returns WEB-INF/jsp/welcome.jsp
    }
}