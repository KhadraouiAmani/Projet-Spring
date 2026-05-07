package com.tp3.portailetudiants.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.net.URI;
import java.time.Instant;

//@ControllerAdvice
public class GlobalExceptionHandler {

    // --- Gestion de l'erreur spécifique (Exemple 7 - Innovation Spring 6) ---
    @ExceptionHandler(EventNotFoundException.class)
    public ProblemDetail handleNotFound(EventNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Événement Introuvable");
        pd.setType(URI.create("https://etudiant-portal.com/errors/not-found"));
        pd.setProperty("timestamp", Instant.now()); // On peut ajouter des propriétés personnalisées
        return pd;
    }

    // --- Gestion de TOUTES les autres erreurs système (RuntimeException) ---
    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntime(RuntimeException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        pd.setTitle("Erreur Système Inattendue");
        pd.setType(URI.create("https://etudiant-portal.com/errors/system-error"));
        pd.setProperty("guide", "Contactez l'administrateur Islem");
        return pd;
    }
}