package it.turingrubrica.app.repo.memory;

import it.turingrubrica.app.domain.Utente;
import it.turingrubrica.app.repo.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, Utente> users = new HashMap<>();


    public InMemoryUserRepository() {
// Demo: username=admin, password=admin (hash fittizio per esempio)
        users.put("admin", new Utente("admin", hash("admin")));
    }


    @Override public Utente findByUsername(String username) { return users.get(username); }


    private static String hash(String raw) {
// SEMPLIFICAZIONE: non usare in produzione. Qui basterà come demo.
        return Integer.toHexString(raw.hashCode());
    }


    public static boolean checkPassword(Utente u, String raw) {
        return u != null && u.getPasswordHash().equals(hash(raw));
    }
}