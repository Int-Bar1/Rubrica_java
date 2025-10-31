package it.turingrubrica.app.service;

import it.turingrubrica.app.domain.Utente;
import it.turingrubrica.app.repo.UserRepository;
import it.turingrubrica.app.repo.memory.InMemoryUserRepository;

public class AuthService {
    private final UserRepository users;

    public AuthService(UserRepository users) { this.users = users; }

    public boolean login(String username, String passwordPlain) {
        Utente u = users.findByUsername(username);
        return InMemoryUserRepository.checkPassword(u, passwordPlain);
    }
}