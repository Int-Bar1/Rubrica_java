package it.turingrubrica.app.repo;

import it.turingrubrica.app.domain.Utente;

public interface UserRepository {
    Utente findByUsername(String username);
}
