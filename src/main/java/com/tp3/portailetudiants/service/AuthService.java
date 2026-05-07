package com.tp3.portailetudiants.service;

import com.tp3.portailetudiants.model.User;
import com.tp3.portailetudiants.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public void register(User user) {
        // Logique métier : on pourrait crypter le mot de passe ici
        userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}