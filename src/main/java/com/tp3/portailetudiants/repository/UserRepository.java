package com.tp3.portailetudiants.repository;

import com.tp3.portailetudiants.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Cette méthode permettra à Islem de vérifier l'email lors de la connexion
    // Spring génère automatiquement : SELECT * FROM users WHERE email = ?
    User findByEmail(String email);

    // Utile pour ton Exemple 5 (Validation) : vérifier si l'email existe déjà à l'inscription
    boolean existsByEmail(String email);
}