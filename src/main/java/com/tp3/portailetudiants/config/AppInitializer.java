package com.tp3.portailetudiants.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // Pour les configs de base de données (Amani plus tard)
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // Charge la configuration Web
        return new Class[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        // Toutes les URLs seront gérées par Spring
        return new String[] { "/" };
    }
}